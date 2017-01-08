package br.com.libertsolutions.libertvendas.app.data.sync;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import br.com.libertsolutions.libertvendas.app.BuildConfig;
import br.com.libertsolutions.libertvendas.app.DataInjection;
import br.com.libertsolutions.libertvendas.app.PresentationInjection;
import br.com.libertsolutions.libertvendas.app.data.cliente.ClienteService;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.data.vendedor.VendedorRepositories;
import br.com.libertsolutions.libertvendas.app.domain.dto.ClienteDto;
import br.com.libertsolutions.libertvendas.app.domain.entity.ClienteEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.VendedorEntity;
import br.com.libertsolutions.libertvendas.app.domain.factory.ClienteFactory;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
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

/**
 * @author Filipe Bezerra
 */
public class SyncTaskService extends GcmTaskService {

    public static final String SYNC_CUSTOMERS = "sync_customers";
    public static final String SYNC_ORDERS = "sync_orders";

    @StringDef({ SYNC_CUSTOMERS, SYNC_ORDERS }) public @interface SyncType {}

    private static final long TASK_PERIOD_IN_SECONDS = TimeUnit.MINUTES.toSeconds(30);

    private Vendedor mVendedorLogado;

    private Realm mRealm;

    private ClienteService mClienteService;

    private SettingsRepository mSettingsRepository;

    @Override public void onInitializeTasks() {
        Timber.d("initializing sync service");
    }

    @Override public int onRunTask(final TaskParams taskParams) {
        final String tag = taskParams.getTag();
        Timber.d("running sync service with tag %s", tag);

        int result = GcmNetworkManager.RESULT_SUCCESS;

        switch (tag) {
            case SYNC_CUSTOMERS: {
                result = doSyncCustomers();
                break;
            }

            case SYNC_ORDERS: {
                result = doSyncOrders();
                break;
            }
        }

        EventBus.getDefault().post(SyncEvent.newEvent(tag, result));

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
                            Timber.e("post customer request was not successful with %d %s",
                                    response.code(), response.message());

                            return GcmNetworkManager.RESULT_FAILURE;
                        }

                        Timber.d("post customer request done successfully");
                        updateNewCustomer(newCustomer, response);
                    } catch (IOException e) {
                        Timber.e(e, "could not done post customer request");
                        return GcmNetworkManager.RESULT_FAILURE;
                    }
                }
            } else {
                Timber.d("no new customers found to sync", newCustomers.size());
            }
            //endregion

            //region patch
            final RealmResults<ClienteEntity> changedCustomers = queryChangedCustomers();

            if (!changedCustomers.isEmpty()) {
                Timber.d("found %d changed customers to sync", changedCustomers.size());

                try {
                    Response<List<ClienteDto>> response = executePatch(changedCustomers);

                    if (!response.isSuccessful()) {
                        Timber.e("patch customers request was not successful with %d %s",
                                response.code(), response.message());

                        return GcmNetworkManager.RESULT_FAILURE;
                    }

                    Timber.d("patch customers request done successfully");
                    updateChangedCustomers(response);
                } catch (IOException e) {
                    Timber.e(e, "could not done patch customers request");
                    return GcmNetworkManager.RESULT_FAILURE;
                }
            } else {
                Timber.d("no changed customers found to sync", changedCustomers.size());
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
        return getClienteService()
                .post(
                        getLoggedUser().getEmpresaSelecionada().getCnpj(),
                        ClienteFactory.createDto(newCustomer))
                .execute();
    }

    private void updateNewCustomer(ClienteEntity newCustomer, Response<ClienteDto> response) {
        final ClienteDto body = response.body();

        Timber.d("committing changes to customers dated from %s to local database with id %d",
                body.ultimaAlteracao, body.idCliente);

        newCustomer.setIdCliente(body.idCliente);
        newCustomer.setUltimaAlteracao(body.ultimaAlteracao);
        newCustomer.setStatus(Cliente.STATUS_SINCRONIZADO);

        getRealm().executeTransaction(realm -> realm.copyToRealmOrUpdate(newCustomer));
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
        return getClienteService()
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

            Timber.d("committing changes to customers dated from %s to local database with id %id",
                    dto.ultimaAlteracao, dto.idCliente);

            customer.setUltimaAlteracao(dto.ultimaAlteracao);
            customer.setStatus(Cliente.STATUS_SINCRONIZADO);

            getRealm().executeTransaction(realm -> realm.copyToRealmOrUpdate(customer));
            Timber.d("commit done successfully");
        }
    }

    private int doSyncOrders() {
        return GcmNetworkManager.RESULT_SUCCESS;
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
        if (mRealm == null) {
            mRealm = Realm.getDefaultInstance();
        }
        return mRealm;
    }

    private void closeRealm() {
        if (mRealm != null && !mRealm.isClosed()) {
            mRealm.close();
        }
    }

    private ClienteService getClienteService() {
        if (mClienteService == null) {
            mClienteService = DataInjection.RemoteRepositories.provideClienteService();
        }
        return mClienteService;
    }

    public static boolean schedule(@NonNull Context context, @SyncType String syncType) {
        try {
            PeriodicTask periodic = new PeriodicTask.Builder()
                    .setService(SyncTaskService.class)
                    //repeat every 120 seconds
                    .setPeriod(BuildConfig.DEBUG ? 30 : TASK_PERIOD_IN_SECONDS)
                    //tag that is unique to this task (can be used to cancel task)
                    .setTag(syncType)
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
