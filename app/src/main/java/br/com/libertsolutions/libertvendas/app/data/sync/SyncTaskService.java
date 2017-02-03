package br.com.libertsolutions.libertvendas.app.data.sync;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.PresentationInjection;
import br.com.libertsolutions.libertvendas.app.data.vendedor.VendedorRepositories;
import br.com.libertsolutions.libertvendas.app.domain.dto.ClienteDto;
import br.com.libertsolutions.libertvendas.app.domain.dto.ItemPedidoDto;
import br.com.libertsolutions.libertvendas.app.domain.dto.PedidoDto;
import br.com.libertsolutions.libertvendas.app.domain.entity.ClienteEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.ItemPedidoEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.PedidoEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.VendedorEntity;
import br.com.libertsolutions.libertvendas.app.domain.factory.ClienteFactory;
import br.com.libertsolutions.libertvendas.app.domain.factory.PedidoFactory;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;
import com.google.android.gms.gcm.TaskParams;
import io.realm.Realm;
import io.realm.RealmResults;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import retrofit2.Response;
import timber.log.Timber;

import static br.com.libertsolutions.libertvendas.app.DataInjection.RemoteRepositories.provideClienteService;
import static br.com.libertsolutions.libertvendas.app.DataInjection.RemoteRepositories.providePedidoService;
import static br.com.libertsolutions.libertvendas.app.PresentationInjection.provideContext;
import static br.com.libertsolutions.libertvendas.app.PresentationInjection.provideEventBus;
import static br.com.libertsolutions.libertvendas.app.PresentationInjection.provideSettingsRepository;

/**
 * @author Filipe Bezerra
 */
public class SyncTaskService extends GcmTaskService {

    public static boolean schedule(@NonNull Context context) {
        try {
            final long syncPeriod = TimeUnit.MINUTES.toSeconds(
                    PresentationInjection.provideSettingsRepository().getSyncPeriod());

            PeriodicTask periodic = new PeriodicTask.Builder()
                    .setService(SyncTaskService.class)
                    //repeat every 'n' minutes (default 30 minutes)
                    .setPeriod(syncPeriod)
                    //tag that is unique to this task (can be used to cancel task)
                    .setTag(SyncTaskService.class.getSimpleName())
                    //whether the task persists after device reboot
                    .setPersisted(true)
                    //if another task with same tag is already scheduled, replace it with this task
                    .setUpdateCurrent(true)
                    //set required network state, this line is optional
                    .setRequiredNetwork(Task.NETWORK_STATE_CONNECTED)
                    //request that charging must be connected, this line is optional
                    .setRequiresCharging(false)
                    .build();

            GcmNetworkManager
                    .getInstance(context.getApplicationContext())
                    .schedule(periodic);
            Timber.v("sync service scheduled with period of %d minutes",
                    TimeUnit.SECONDS.toMinutes(periodic.getPeriod()));
            return true;
        } catch (Exception e) {
            Timber.e(e, "scheduling sync service failed");
            return false;
        }
    }

    public static boolean cancelAll(@NonNull Context context) {
        try {
            GcmNetworkManager
                    .getInstance(context.getApplicationContext())
                    .cancelAllTasks(SyncTaskService.class);
            Timber.v("sync service cancelled");
            return true;
        } catch (Exception e) {
            Timber.e(e, "cancelling sync service failed");
            return false;
        }
    }

    private Realm mRealm;

    private Vendedor mVendedorLogado;

    @Override public void onInitializeTasks() {
        Timber.d("initializing sync service");
    }

    @Override public int onRunTask(final TaskParams taskParams) {
        Timber.d("running sync service");

        if (!provideSettingsRepository().isUserLoggedIn()) {
            Timber.i("No user logged, sync will be cancelled");
            SyncTaskService.cancelAll(provideContext());
            return GcmNetworkManager.RESULT_FAILURE;
        }

        try {
            //region sync changes in customers database
            final RealmResults<ClienteEntity> changedCustomers = queryChangedCustomers();
            if (!changedCustomers.isEmpty()) {
                Timber.d("found %d changed customers to sync", changedCustomers.size());

                Response<List<ClienteDto>> response = patchChangedCustomers(changedCustomers);

                if (response != null) {
                    if (!response.isSuccessful()) {
                        Timber.e("customers patch request failure: %d %s",
                                response.code(), response.message());
                    } else {
                        Timber.d("customers patch request done successfully");
                        updateChangedCustomers(response);
                        provideEventBus().post(SyncCustomersEvent.newEvent());
                    }
                }
            } else {
                Timber.d("no changed customers found to sync");
            }
            //endregion

            //region sync new customers in database
            final RealmResults<ClienteEntity> newCustomers = queryNewCustomers();

            if (!newCustomers.isEmpty()) {
                Timber.d("found %d new customers to sync", newCustomers.size());

                boolean notifyChanges = false;
                for (ClienteEntity newCustomer : newCustomers) {
                    Response<ClienteDto> response = postNewCustomer(newCustomer);

                    if (response != null) {
                        if (!response.isSuccessful()) {
                            Timber.e("customer post request failure: %d %s",
                                    response.code(), response.message());
                        } else {
                            Timber.d("customer post request done successfully");
                            updateNewCustomer(newCustomer, response);
                            notifyChanges = true;
                        }
                    }
                }

                if (notifyChanges) {
                    provideEventBus().post(SyncCustomersEvent.newEvent());
                }
            } else {
                Timber.d("no new customers found to sync");
            }
            //endregion

            //region sync pending orders
            final RealmResults<PedidoEntity> pendingOrders = queryPendingOrders();

            if (!pendingOrders.isEmpty()) {
                Timber.d("found %d pending orders to sync", pendingOrders.size());

                boolean notifyChanges = false;
                for (PedidoEntity order : pendingOrders) {
                    if (order.getCliente().getStatus() == Cliente.STATUS_CRIADO) {
                        Timber.d("can\'t sync order id %d, customer is new and not synced",
                                order.getId());
                        continue;
                    }

                    Response<PedidoDto> response = postPendingOrders(order);

                    if (response != null) {
                        if (!response.isSuccessful()) {
                            Timber.e("order post request failure: %d %s",
                                    response.code(), response.message());

                            return GcmNetworkManager.RESULT_RESCHEDULE;
                        }

                        Timber.d("order post request done successfully");
                        if (updatePendingOrder(order, response)) {
                            notifyChanges = true;
                        }
                    } else {
                        return GcmNetworkManager.RESULT_RESCHEDULE;
                    }
                }

                if (notifyChanges) {
                    provideEventBus().post(SyncOrdersEvent.newEvent());
                }
            } else {
                Timber.d("no pending orders found to sync");
            }
            //endregion
        } finally {
            closeRealm();
        }

        return GcmNetworkManager.RESULT_SUCCESS;
    }

    private RealmResults<ClienteEntity> queryChangedCustomers() {
        return getRealm().where(ClienteEntity.class)
                .equalTo("cnpjEmpresa", getLoggedUser().getEmpresaSelecionada().getCnpj())
                .equalTo("cpfCnpjVendedor", getLoggedUser().getCpfCnpj())
                .equalTo("status", Cliente.STATUS_MODIFICADO)
                .findAll();
    }

    private Realm getRealm() {
        if (mRealm == null || mRealm.isClosed()) {
            mRealm = Realm.getDefaultInstance();
        }
        return mRealm;
    }

    private Vendedor getLoggedUser() {
        if (mVendedorLogado == null) {
            VendedorEntity vendedor = getRealm().where(VendedorEntity.class)
                    .equalTo("idVendedor", provideSettingsRepository().getLoggedInUser())
                    .findFirst();
            mVendedorLogado = VendedorRepositories.getMapper().toViewObject(vendedor);
        }
        return mVendedorLogado;
    }

    private Response<List<ClienteDto>> patchChangedCustomers(
            RealmResults<ClienteEntity> changedCustomers) {
        try {
            return provideClienteService()
                    .patch(
                            getLoggedUser().getEmpresaSelecionada().getCnpj(),
                            ClienteFactory.toDtoList(changedCustomers))
                    .execute();
        } catch (IOException e) {
            Timber.e(e, "server failure while doing customers patch request");
        } catch (RuntimeException e) {
            Timber.e(e, "error creating the request or decoding the response from customers "
                    + "patch request");
        }
        return null;
    }

    private void updateChangedCustomers(Response<List<ClienteDto>> response) {
        Timber.d("committing changes to customers database");
        try {
            for (ClienteDto dto : response.body()) {
                ClienteEntity customer = getRealm().where(ClienteEntity.class)
                        .equalTo("idCliente", dto.idCliente)
                        .findFirst();

                Timber.d("changes dated from %s to local database with id %d",
                        dto.ultimaAlteracao, dto.idCliente);

                getRealm().executeTransaction(realm -> {
                    customer.setUltimaAlteracao(dto.ultimaAlteracao);
                    customer.setStatus(Cliente.STATUS_SINCRONIZADO);
                    realm.copyToRealmOrUpdate(customer);
                });

                Timber.d("commit done successfully");
            }
        } catch (RuntimeException e) {
            Timber.e(e, "could not commit changes to customers database");
        }
    }

    private RealmResults<ClienteEntity> queryNewCustomers() {
        return getRealm().where(ClienteEntity.class)
                .equalTo("cnpjEmpresa", getLoggedUser().getEmpresaSelecionada().getCnpj())
                .equalTo("cpfCnpjVendedor", getLoggedUser().getCpfCnpj())
                .equalTo("status", Cliente.STATUS_CRIADO)
                .findAll();
    }

    private Response<ClienteDto> postNewCustomer(ClienteEntity newCustomer) {
        try {
            return provideClienteService()
                    .post(
                            getLoggedUser().getEmpresaSelecionada().getCnpj(),
                            ClienteFactory.toDto(newCustomer))
                    .execute();
        } catch (IOException e) {
            Timber.e(e, "server failure while doing customer post request");
        } catch (RuntimeException e) {
            Timber.e(e, "error creating the request or decoding the response from customer "
                    + "post request");
        }
        return null;
    }

    private void updateNewCustomer(ClienteEntity newCustomer, Response<ClienteDto> response) {
        Timber.d("committing changes to customers database");
        try {
            final ClienteDto body = response.body();

            Timber.d("changes dated from %s to local database with id %d",
                    body.ultimaAlteracao, body.idCliente);

            getRealm().executeTransaction(realm -> {
                newCustomer.setIdCliente(body.idCliente);
                newCustomer.setUltimaAlteracao(body.ultimaAlteracao);
                newCustomer.setStatus(Cliente.STATUS_SINCRONIZADO);
                realm.copyToRealmOrUpdate(newCustomer);
            });

            Timber.d("commit done successfully");
        } catch (RuntimeException e) {
            Timber.e(e, "could not commit changes to customers database");
        }
    }

    private RealmResults<PedidoEntity> queryPendingOrders() {
        return getRealm().where(PedidoEntity.class)
                .equalTo("cnpjEmpresa", getLoggedUser().getEmpresaSelecionada().getCnpj())
                .equalTo("cpfCnpjVendedor", getLoggedUser().getCpfCnpj())
                .equalTo("status", Pedido.STATUS_PENDENTE)
                .findAll();
    }

    private Response<PedidoDto> postPendingOrders(PedidoEntity order) {
        try {
            return providePedidoService()
                    .post(
                            getLoggedUser().getEmpresaSelecionada().getCnpj(),
                            getLoggedUser().getCpfCnpj(),
                            PedidoFactory.createDto(order))
                    .execute();
        } catch (IOException e) {
            Timber.e(e, "server failure while doing order post request");
        } catch (RuntimeException e) {
            Timber.e(e, "error creating the request or decoding the response from order "
                    + "post request");
        }
        return null;
    }

    private boolean updatePendingOrder(PedidoEntity order, Response<PedidoDto> response) {
        Timber.d("committing changes to customers database");
        try {
            final PedidoDto body = response.body();

            Timber.d("changes dated from %s to local database with id %d",
                    body.ultimaAlteracao, body.idPedido);

            getRealm().executeTransaction(realm -> {
                order.setIdPedido(body.idPedido);
                order.setUltimaAlteracao(body.ultimaAlteracao);
                order.setStatus(Pedido.STATUS_ENVIADO);

                for (ItemPedidoDto itemDto : body.itens) {
                    for (ItemPedidoEntity orderItem : order.getItens()) {
                        if (itemDto.appKey.equals(orderItem.getId())) {
                            orderItem.setIdItem(itemDto.idItem);
                            orderItem.setUltimaAlteracao(itemDto.ultimaAlteracao);
                            break;
                        }
                    }
                }

                realm.copyToRealmOrUpdate(order);
            });

            Timber.d("commit done successfully");
            return true;
        } catch (RuntimeException e) {
            Timber.e(e, "could not commit changes to orders database");
        }
        return false;
    }

    private void closeRealm() {
        if (mRealm != null && !mRealm.isClosed()) {
            mRealm.close();
        }
    }
}
