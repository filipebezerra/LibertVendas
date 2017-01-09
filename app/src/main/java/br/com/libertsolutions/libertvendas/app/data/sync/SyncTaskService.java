package br.com.libertsolutions.libertvendas.app.data.sync;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.BuildConfig;
import br.com.libertsolutions.libertvendas.app.PresentationInjection;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
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
import org.greenrobot.eventbus.EventBus;
import retrofit2.Response;
import timber.log.Timber;

import static br.com.libertsolutions.libertvendas.app.DataInjection.RemoteRepositories.provideClienteService;
import static br.com.libertsolutions.libertvendas.app.DataInjection.RemoteRepositories.providePedidoService;

/**
 * @author Filipe Bezerra
 */
public class SyncTaskService extends GcmTaskService {

    private static final long TASK_PERIOD_IN_SECONDS = TimeUnit.MINUTES.toSeconds(30);

    private Vendedor mVendedorLogado;

    private Realm mRealm;

    private SettingsRepository mSettingsRepository;

    @Override public void onInitializeTasks() {
        Timber.d("initializing sync service");
    }

    @Override public int onRunTask(final TaskParams taskParams) {
        Timber.d("running sync service");

        int result = doSyncCustomers();

        if (result != GcmNetworkManager.RESULT_SUCCESS) {
            EventBus.getDefault().post(SyncEvent.newEvent(result));
            return result;
        }

        result = doSyncOrders();

        EventBus.getDefault().post(SyncEvent.newEvent(result));
        return result;
    }

    private int doSyncCustomers() {
        Timber.d("syncing customers");

        try {
            //region post
            final RealmResults<ClienteEntity> newCustomers = queryNewCustomers();

            if (!newCustomers.isEmpty()) {
                Timber.d("found %d new customers to sync", newCustomers.size());

                for (ClienteEntity newCustomer : newCustomers) {
                    try {
                        Response<ClienteDto> response = executePost(newCustomer);

                        if (!response.isSuccessful()) {
                            Timber.e("customer post request failure: %d %s",
                                    response.code(), response.message());

                            return GcmNetworkManager.RESULT_FAILURE;
                        }

                        Timber.d("customer post request done successfully");
                        updateNewCustomer(newCustomer, response);
                    } catch (RuntimeException e) {
                        Timber.e(e, "error creating the request or decoding the response "
                                + "from customer post request");
                        return GcmNetworkManager.RESULT_FAILURE;
                    } catch (IOException e) {
                        Timber.e(e, "server failure while doing customer post request");
                        return GcmNetworkManager.RESULT_FAILURE;
                    }
                }
            } else {
                Timber.d("no new customers found to sync");
            }
            //endregion

            //region patch
            final RealmResults<ClienteEntity> changedCustomers = queryChangedCustomers();

            if (!changedCustomers.isEmpty()) {
                Timber.d("found %d changed customers to sync", changedCustomers.size());

                try {
                    Response<List<ClienteDto>> response = executePatch(changedCustomers);

                    if (!response.isSuccessful()) {
                        Timber.e("customers patch request failure: %d %s",
                                response.code(), response.message());

                        return GcmNetworkManager.RESULT_FAILURE;
                    }

                    Timber.d("customers patch request done successfully");
                    updateChangedCustomers(response);
                } catch (RuntimeException e) {
                    Timber.e(e, "error creating the request or decoding the response "
                            + "from customers patch request");
                    return GcmNetworkManager.RESULT_FAILURE;
                } catch (IOException e) {
                    Timber.e(e, "server failure while doing customers patch request");
                    return GcmNetworkManager.RESULT_FAILURE;
                }
            } else {
                Timber.d("no changed customers found to sync");
            }
            //endregion
        } finally {
            closeRealm();
        }

        return GcmNetworkManager.RESULT_SUCCESS;
    }

    private RealmResults<ClienteEntity> queryNewCustomers() {
        return getRealm().where(ClienteEntity.class)
                .equalTo("cnpjEmpresa", getLoggedUser().getEmpresaSelecionada().getCnpj())
                .equalTo("cpfCnpjVendedor", getLoggedUser().getCpfCnpj())
                .equalTo("status", Cliente.STATUS_CRIADO)
                .findAll();
    }

    private Response<ClienteDto> executePost(ClienteEntity newCustomer) throws IOException {
        return provideClienteService()
                .post(
                        getLoggedUser().getEmpresaSelecionada().getCnpj(),
                        ClienteFactory.createDto(newCustomer))
                .execute();
    }

    private void updateNewCustomer(ClienteEntity newCustomer, Response<ClienteDto> response) {
        final ClienteDto body = response.body();

        Timber.d("committing changes to customers dated from %s to local database with id %d",
                body.ultimaAlteracao, body.idCliente);

        getRealm().executeTransaction(realm -> {
            newCustomer.setIdCliente(body.idCliente);
            newCustomer.setUltimaAlteracao(body.ultimaAlteracao);
            newCustomer.setStatus(Cliente.STATUS_SINCRONIZADO);

            realm.copyToRealmOrUpdate(newCustomer);
        });
        Timber.d("commit done successfully");
    }

    private RealmResults<ClienteEntity> queryChangedCustomers() {
        return getRealm().where(ClienteEntity.class)
                .equalTo("cnpjEmpresa", getLoggedUser().getEmpresaSelecionada().getCnpj())
                .equalTo("cpfCnpjVendedor", getLoggedUser().getCpfCnpj())
                .equalTo("status", Cliente.STATUS_MODIFICADO)
                .findAll();
    }

    private Response<List<ClienteDto>> executePatch(RealmResults<ClienteEntity> changedCustomers)
            throws IOException {
        return provideClienteService()
                .patch(
                        getLoggedUser().getEmpresaSelecionada().getCnpj(),
                        ClienteFactory.createListDto(changedCustomers))
                .execute();
    }

    private void updateChangedCustomers(Response<List<ClienteDto>> response) {
        for (ClienteDto dto : response.body()) {
            ClienteEntity customer = getRealm().where(ClienteEntity.class)
                    .equalTo("idCliente", dto.idCliente)
                    .findFirst();

            Timber.d("committing changes to customers dated from %s to local database with id %d",
                    dto.ultimaAlteracao, dto.idCliente);

            getRealm().executeTransaction(new Realm.Transaction() {
                @Override public void execute(final Realm realm) {
                    customer.setUltimaAlteracao(dto.ultimaAlteracao);
                    customer.setStatus(Cliente.STATUS_SINCRONIZADO);

                    realm.copyToRealmOrUpdate(customer);
                }
            });
            Timber.d("commit done successfully");
        }
    }

    private int doSyncOrders() {
        Timber.d("syncing orders");

        try {
            //region post
            final RealmResults<PedidoEntity> pendingOrders = queryPendingOrders();

            if (!pendingOrders.isEmpty()) {
                Timber.d("found %d pending orders to sync", pendingOrders.size());

                for (PedidoEntity order : pendingOrders) {
                    try {
                        Response<PedidoDto> response = executePost(order);

                        if (!response.isSuccessful()) {
                            Timber.e("order post request failure: %d %s",
                                    response.code(), response.message());

                            return GcmNetworkManager.RESULT_FAILURE;
                        }

                        Timber.d("order post request done successfully");
                        updatePendingOrder(order, response);
                    } catch (RuntimeException e) {
                        Timber.e(e, "error creating the request or decoding the response "
                                + "from order post request");
                        return GcmNetworkManager.RESULT_FAILURE;
                    } catch (IOException e) {
                        Timber.e(e, "server failure while doing order post request");
                        return GcmNetworkManager.RESULT_FAILURE;
                    }
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

    private RealmResults<PedidoEntity> queryPendingOrders() {
        return getRealm().where(PedidoEntity.class)
                .equalTo("cnpjEmpresa", getLoggedUser().getEmpresaSelecionada().getCnpj())
                .equalTo("cpfCnpjVendedor", getLoggedUser().getCpfCnpj())
                .equalTo("status", Pedido.STATUS_PENDENTE)
                .findAll();
    }

    private Response<PedidoDto> executePost(PedidoEntity order) throws IOException {
        return providePedidoService()
                .post(
                        getLoggedUser().getEmpresaSelecionada().getCnpj(),
                        getLoggedUser().getCpfCnpj(),
                        PedidoFactory.createDto(order))
                .execute();
    }

    private void updatePendingOrder(PedidoEntity order, Response<PedidoDto> response) {
        final PedidoDto body = response.body();

        Timber.d("committing changes to orders dated from %s to local database with id %d",
                body.ultimaAlteracao, body.idPedido);

        getRealm().executeTransaction(realm -> {
            order.setIdPedido(body.idPedido);
            order.setUltimaAlteracao(body.ultimaAlteracao);
            order.setStatus(Pedido.STATUS_ENVIADO);

            for (ItemPedidoDto itemDto : body.itens) {
                for(ItemPedidoEntity orderItem : order.getItens()) {
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
    }

    private Vendedor getLoggedUser() {
        if (mVendedorLogado == null) {
            VendedorEntity vendedor = getRealm().where(VendedorEntity.class)
                    .equalTo("idVendedor", getSettingsRepository().getLoggedInUser())
                    .findFirst();
            mVendedorLogado = VendedorRepositories.getMapper().toViewObject(vendedor);
        }
        return mVendedorLogado;
    }

    private SettingsRepository getSettingsRepository() {
        if (mSettingsRepository == null) {
            mSettingsRepository = PresentationInjection.provideSettingsRepository();
        }
        return mSettingsRepository;
    }

    private Realm getRealm() {
        if (mRealm == null || mRealm.isClosed()) {
            mRealm = Realm.getDefaultInstance();
        }
        return mRealm;
    }

    private void closeRealm() {
        if (mRealm != null && !mRealm.isClosed()) {
            mRealm.close();
        }
    }

    public static boolean schedule(@NonNull Context context) {
        try {
            PeriodicTask periodic = new PeriodicTask.Builder()
                    .setService(SyncTaskService.class)
                    //repeat every 30 minutes
                    .setPeriod(BuildConfig.DEBUG ? 30 : TASK_PERIOD_IN_SECONDS)
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
            Timber.v("sync service scheduled");
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
}
