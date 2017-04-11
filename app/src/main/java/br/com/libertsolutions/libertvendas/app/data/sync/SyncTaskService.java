package br.com.libertsolutions.libertvendas.app.data.sync;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.data.company.customer.CompanyCustomerRepository;
import br.com.libertsolutions.libertvendas.app.data.customer.CustomerByIdSpecification;
import br.com.libertsolutions.libertvendas.app.data.company.customer.CustomersByCompanySpecification;
import br.com.libertsolutions.libertvendas.app.data.company.paymentmethod.CompanyPaymentMethodRepository;
import br.com.libertsolutions.libertvendas.app.data.customer.CustomerApi;
import br.com.libertsolutions.libertvendas.app.data.customer.CustomerRepository;
import br.com.libertsolutions.libertvendas.app.data.order.OrderApi;
import br.com.libertsolutions.libertvendas.app.data.order.OrderRepository;
import br.com.libertsolutions.libertvendas.app.data.order.OrdersByUserSpecification;
import br.com.libertsolutions.libertvendas.app.data.paymentmethod.PaymentMethodByIdSpecification;
import br.com.libertsolutions.libertvendas.app.data.paymentmethod.PaymentMethodRepository;
import br.com.libertsolutions.libertvendas.app.data.product.ProductRepository;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.domain.dto.OrderDto;
import br.com.libertsolutions.libertvendas.app.domain.dto.OrderItemDto;
import br.com.libertsolutions.libertvendas.app.domain.dto.ServerStatus;
import br.com.libertsolutions.libertvendas.app.domain.pojo.CompanyCustomer;
import br.com.libertsolutions.libertvendas.app.domain.pojo.CompanyPaymentMethod;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Customer;
import br.com.libertsolutions.libertvendas.app.domain.pojo.CustomerStatus;
import br.com.libertsolutions.libertvendas.app.domain.pojo.LoggedUser;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Order;
import br.com.libertsolutions.libertvendas.app.domain.pojo.OrderItem;
import br.com.libertsolutions.libertvendas.app.domain.pojo.OrderStatus;
import br.com.libertsolutions.libertvendas.app.domain.pojo.PaymentMethod;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Product;
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

import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.provideCompanyCustomerRepository;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.provideCompanyPaymentMethodRepository;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.provideCustomerRepository;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.providePaymentMethodRepository;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.provideProductRepository;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.providerOrderRepository;
import static br.com.libertsolutions.libertvendas.app.data.RemoteDataInjector.provideCustomerApi;
import static br.com.libertsolutions.libertvendas.app.data.RemoteDataInjector.provideOrderApi;
import static br.com.libertsolutions.libertvendas.app.data.RemoteDataInjector.providePaymentMethodApi;
import static br.com.libertsolutions.libertvendas.app.data.RemoteDataInjector.provideProductApi;
import static br.com.libertsolutions.libertvendas.app.data.RemoteDataInjector.provideSyncApi;
import static br.com.libertsolutions.libertvendas.app.presentation.PresentationInjector.provideSettingsRepository;
import static java.util.Collections.emptyList;

/**
 * @author Filipe Bezerra
 */
public class SyncTaskService extends GcmTaskService {

    private SettingsRepository settingsRepository;

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

        settingsRepository = provideSettingsRepository();

        if (!settingsRepository.isUserLoggedIn()) {
            Timber.i("No user logged, sync will be cancelled");
            SyncTaskService.cancelAll(this);
            return GcmNetworkManager.RESULT_FAILURE;
        }

        final LoggedUser loggedUser = settingsRepository
                .getLoggedUser()
                .toBlocking()
                .single();

        final int companyId = loggedUser.getDefaultCompany().getCompanyId();
        final String companyCnpj = loggedUser.getDefaultCompany().getCnpj();

        final CustomerRepository customerRepository = provideCustomerRepository();
        final CustomerApi customerApi = provideCustomerApi();

        final String lastSyncTime = settingsRepository.getLastSyncTime();

        //region sending created customers
        final List<Customer> createdCustomers = customerRepository
                .query(new CustomersByCompanySpecification(companyId)
                        .byStatus(CustomerStatus.STATUS_CREATED))
                .toBlocking()
                .firstOrDefault(emptyList());

        if (!createdCustomers.isEmpty()) {
            Timber.i("%d new customers ready to sync", createdCustomers.size());

            for (final Customer newCustomer : createdCustomers) {
                try {
                    final Response<Customer> response = customerApi
                            .createCustomer(companyCnpj, newCustomer)
                            .execute();

                    if (response.isSuccessful()) {
                        customerRepository
                                .save(response.body())
                                .toBlocking()
                                .single();
                    } else {
                        Timber.i("Unsuccessful new customer sync. %s", response.message());
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
        //endregion

        //region sending modified customers
        final List<Customer> modifiedCustomers = customerRepository
                .query(new CustomersByCompanySpecification(companyId)
                        .byStatus(CustomerStatus.STATUS_MODIFIED))
                .toBlocking()
                .firstOrDefault(emptyList());

        if (!modifiedCustomers.isEmpty()) {
            Timber.i("%d modified customers ready to sync", modifiedCustomers.size());

            try {
                Response<List<Customer>> response = customerApi
                        .updateCustomers(companyCnpj, modifiedCustomers)
                        .execute();

                if (response.isSuccessful()) {
                    customerRepository
                            .save(response.body())
                            .toBlocking()
                            .single();
                } else {
                    Timber.i("Unsuccessful update customer sync. %s", response.message());
                }
            } catch (IOException e) {
                Timber.e(e, "Server failure while syncing modified customers");
                return GcmNetworkManager.RESULT_RESCHEDULE;
            } catch (RuntimeException e) {
                Timber.e(e, "Unknown error while syncing modified customers");
                return GcmNetworkManager.RESULT_RESCHEDULE;
            }
        }
        //endregion

        //region sending orders
        if (settingsRepository.getSettings().isAutomaticallySyncOrders()) {
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
                        } else {
                            Timber.i("Unsuccessful orders sync. %s", response.message());
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
        } else {
            Timber.i("Orders are not enabled to sync automatically");
        }
        //endregion

        //region getting customer updates
        try {
            Response<List<Customer>> response = customerApi
                    .getUpdates(companyCnpj, lastSyncTime)
                    .execute();

            if (response.isSuccessful()) {
                final List<Customer> updatedCustomers = response.body();

                CompanyCustomerRepository companyCustomerRepository
                        = provideCompanyCustomerRepository();

                for (Customer customer : updatedCustomers) {
                    Integer customerId = customer.getCustomerId();
                    Customer result = customerRepository
                            .findFirst(new CustomerByIdSpecification(customerId))
                            .toBlocking()
                            .single();

                    if (result != null) {
                        customer
                                .withId(result.getId())
                                .withStatus(result.getStatus());

                        customerRepository
                                .save(customer)
                                .toBlocking()
                                .single();
                    } else {
                        final Customer newCustomer = customerRepository
                                .save(customer)
                                .toBlocking()
                                .single();

                        CompanyCustomer companyCustomer = CompanyCustomer
                                .from(loggedUser.getDefaultCompany(), newCustomer);

                        companyCustomerRepository
                                .save(companyCustomer)
                                .toBlocking()
                                .single();
                    }
                }
            } else {
                Timber.i("Unsuccessful getting customer updates. %s", response.message());
            }
        } catch (IOException e) {
            Timber.e(e, "Server failure while getting customer updates");
        }
        //endregion

        //region getting payment method updates
        try {
            Response<List<PaymentMethod>> response = providePaymentMethodApi()
                    .getUpdates(companyCnpj, lastSyncTime)
                    .execute();

            if (response.isSuccessful()) {
                final List<PaymentMethod> updatedPaymentMethods = response.body();

                PaymentMethodRepository paymentMethodRepository
                        = providePaymentMethodRepository();

                CompanyPaymentMethodRepository companyPaymentMethodRepository
                        = provideCompanyPaymentMethodRepository();

                for (PaymentMethod paymentMethod : updatedPaymentMethods) {
                    Integer paymentMethodId = paymentMethod.getPaymentMethodId();
                    PaymentMethod result = paymentMethodRepository
                            .findFirst(new PaymentMethodByIdSpecification(paymentMethodId))
                            .toBlocking()
                            .single();

                    PaymentMethod newPaymentMethod = paymentMethodRepository
                            .save(paymentMethod)
                            .toBlocking()
                            .single();

                    if (result == null) {
                        CompanyPaymentMethod companyPaymentMethod = CompanyPaymentMethod
                                .from(loggedUser.getDefaultCompany(), newPaymentMethod);

                        companyPaymentMethodRepository
                                .save(companyPaymentMethod)
                                .toBlocking()
                                .single();
                    }
                }
            } else {
                Timber.i("Unsuccessful getting payment method updates. %s", response.message());
            }
        } catch (IOException e) {
            Timber.e(e, "Server failure while getting payment method updates");
        }
        //endregion

        //region getting product updates
        try {
            Response<List<Product>> response = provideProductApi()
                    .getUpdates(companyCnpj, lastSyncTime)
                    .execute();

            if (response.isSuccessful()) {
                final List<Product> updatedProducts = response.body();

                ProductRepository productRepository = provideProductRepository();

                for (Product product : updatedProducts) {
                    productRepository
                            .save(product)
                            .toBlocking()
                            .single();
                }
            } else {
                Timber.i("Unsuccessful getting product updates. %s", response.message());
            }
        } catch (IOException e) {
            Timber.e(e, "Server failure while getting product updates");
        }
        //endregion

        //region updating last sync time
        try {
            final Response<ServerStatus> response = provideSyncApi()
                    .getServerStatus()
                    .execute();

            if (response.isSuccessful()) {
                settingsRepository.setLastSyncTime(response.body().currentTime);
            }
        } catch (IOException e) {
            Timber.e(e, "Server failure while getting server status");
        }
        //endregion

        return GcmNetworkManager.RESULT_SUCCESS;
    }
}
