package br.com.libertsolutions.libertvendas.app.data.sync;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.data.company.customer.CustomersByCompanySpecification;
import br.com.libertsolutions.libertvendas.app.data.customer.CustomerApi;
import br.com.libertsolutions.libertvendas.app.data.customer.CustomerRepository;
import br.com.libertsolutions.libertvendas.app.data.order.OrderApi;
import br.com.libertsolutions.libertvendas.app.data.order.OrderRepository;
import br.com.libertsolutions.libertvendas.app.data.order.OrdersByUserSpecification;
import br.com.libertsolutions.libertvendas.app.domain.dto.OrderDto;
import br.com.libertsolutions.libertvendas.app.domain.dto.OrderItemDto;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Customer;
import br.com.libertsolutions.libertvendas.app.domain.pojo.CustomerStatus;
import br.com.libertsolutions.libertvendas.app.domain.pojo.LoggedUser;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Order;
import br.com.libertsolutions.libertvendas.app.domain.pojo.OrderItem;
import br.com.libertsolutions.libertvendas.app.domain.pojo.OrderStatus;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;
import com.google.android.gms.gcm.TaskParams;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import retrofit2.Response;
import timber.log.Timber;

import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.provideCustomerRepository;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.providerOrderRepository;
import static br.com.libertsolutions.libertvendas.app.data.RemoteDataInjector.provideCustomerApi;
import static br.com.libertsolutions.libertvendas.app.data.RemoteDataInjector.provideOrderApi;
import static br.com.libertsolutions.libertvendas.app.presentation.PresentationInjector.provideSettingsRepository;
import static java.util.Collections.emptyList;

/**
 * @author Filipe Bezerra
 */
public class SyncTaskService extends GcmTaskService {

    public static boolean schedule(@NonNull Context context, @IntRange(from = 0) int periodInMinutes) {
        try {
            final long syncPeriodInSeconds = TimeUnit.MINUTES.toSeconds(periodInMinutes);

            if (provideSettingsRepository().isRunningSyncWith(syncPeriodInSeconds)) {
                Timber.v("sync service already scheduled with period of %d minutes",
                        periodInMinutes);
                return false;
            }

            PeriodicTask periodic = new PeriodicTask.Builder()
                    .setService(SyncTaskService.class)
                    //repeat every 'n' minutes (default 30 minutes)
                    .setPeriod(syncPeriodInSeconds)
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

            provideSettingsRepository().setRunningSyncWith(syncPeriodInSeconds);
            Timber.v("sync service scheduled with period of %d minutes", periodInMinutes);
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
            provideSettingsRepository().setRunningSyncWith(0);
            Timber.v("sync service cancelled");
            return true;
        } catch (Exception e) {
            Timber.e(e, "cancelling sync service failed");
            return false;
        }
    }

    @Override public void onInitializeTasks() {
        Timber.d("initializing sync service");
        SyncTaskService.cancelAll(this);
        SyncTaskService.schedule(this,
                provideSettingsRepository().getSettings().getSyncPeriodicity());
    }

    @Override public int onRunTask(final TaskParams taskParams) {
        Timber.d("running sync service");

        if (!provideSettingsRepository().isUserLoggedIn()) {
            Timber.i("No user logged, sync will be cancelled");
            SyncTaskService.cancelAll(this);
            return GcmNetworkManager.RESULT_FAILURE;
        }

        final LoggedUser loggedUser = provideSettingsRepository()
                .getLoggedUser()
                .toBlocking()
                .single();

        final int companyId = loggedUser.getDefaultCompany().getCompanyId();
        final String companyCnpj = loggedUser.getDefaultCompany().getCnpj();

        final CustomerRepository customerRepository = provideCustomerRepository();
        final CustomerApi customerApi = provideCustomerApi();

        final List<Customer> createdCustomers = customerRepository
                .query(new CustomersByCompanySpecification(companyId)
                        .byStatus(CustomerStatus.STATUS_CREATED))
                .toBlocking()
                .firstOrDefault(emptyList());

        if (!createdCustomers.isEmpty()) {
            for (final Customer newCustomer : createdCustomers) {
                try {
                    final Response<Customer> response = customerApi
                            .createCustomer(companyCnpj, newCustomer)
                            .execute();

                    if (response.isSuccessful()) {
                        customerRepository.save(response.body())
                                .toBlocking()
                                .single();
                    }
                } catch (IOException e) {
                    Timber.e(e, "Server failure while syncing new customers");
                    return GcmNetworkManager.RESULT_RESCHEDULE;
                } catch (RuntimeException e) {
                    Timber.e(e, "Unknown error while syncing new customers");
                    return GcmNetworkManager.RESULT_RESCHEDULE;
                }
            }
        }

        final List<Customer> modifiedCustomers = customerRepository
                .query(new CustomersByCompanySpecification(companyId)
                        .byStatus(CustomerStatus.STATUS_MODIFIED))
                .toBlocking()
                .firstOrDefault(emptyList());

        if (!modifiedCustomers.isEmpty()) {
            try {
                Response<List<Customer>> response = customerApi
                        .updateCustomers(companyCnpj, modifiedCustomers)
                        .execute();

                if (response.isSuccessful()) {
                    customerRepository
                            .save(response.body())
                            .toBlocking()
                            .single();
                }
            } catch (IOException e) {
                Timber.e(e, "Server failure while syncing modified customers");
                return GcmNetworkManager.RESULT_RESCHEDULE;
            } catch (RuntimeException e) {
                Timber.e(e, "Unknown error while syncing modified customers");
                return GcmNetworkManager.RESULT_RESCHEDULE;
            }
        }

        final int salesmanId = loggedUser.getSalesman().getSalesmanId();
        final String salesmanCpfOrCnpj = loggedUser.getSalesman().getCpfOrCnpj();

        final OrderRepository orderRepository = providerOrderRepository();
        final OrderApi orderApi = provideOrderApi();

        List<Order> createdOrModifiedOrders = orderRepository
                .query(new OrdersByUserSpecification(salesmanId, companyId)
                        .byStatusCreatedOrModified())
                .toBlocking()
                .firstOrDefault(Collections.emptyList());

        if (!createdOrModifiedOrders.isEmpty()) {
            for (Order order : createdOrModifiedOrders) {
                try {
                    OrderDto postOrder = order.createPostOrder();

                    Response<OrderDto> response = orderApi
                            .createOrder(companyCnpj, salesmanCpfOrCnpj, postOrder)
                            .execute();

                    if (response.isSuccessful()) {
                        OrderDto syncedOrder = response.body();

                        for (OrderItemDto syncedOrderItem : syncedOrder.items) {
                            for (OrderItem item : order.getItems()) {
                                if (item.getId().compareTo(syncedOrderItem.id) == 0) {
                                    item
                                            .withOrderItemId(syncedOrderItem.orderItemId)
                                            .withLastChangeTime(syncedOrderItem.lastChangeTime);
                                    break;
                                }
                            }
                        }

                        order
                                .withOrderId(syncedOrder.orderId)
                                .withLastChangeTime(syncedOrder.lastChangeTime)
                                .withStatus(OrderStatus.STATUS_SYNCED);

                        orderRepository
                                .save(order)
                                .toBlocking()
                                .first();
                    }
                } catch (IOException e) {
                    Timber.e(e, "Server failure while syncing orders");
                    return GcmNetworkManager.RESULT_RESCHEDULE;
                } catch (RuntimeException e) {
                    Timber.e(e, "Unknown error while syncing orders");
                    return GcmNetworkManager.RESULT_RESCHEDULE;
                }
            }
        }

        return GcmNetworkManager.RESULT_SUCCESS;
    }
}
