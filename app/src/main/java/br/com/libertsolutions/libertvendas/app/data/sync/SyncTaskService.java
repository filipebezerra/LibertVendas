package br.com.libertsolutions.libertvendas.app.data.sync;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.data.company.customer.CompanyCustomerRepository;
import br.com.libertsolutions.libertvendas.app.data.company.customer.CustomersByCompanySpecification;
import br.com.libertsolutions.libertvendas.app.data.company.paymentmethod.CompanyPaymentMethodRepository;
import br.com.libertsolutions.libertvendas.app.data.company.pricetable.CompanyPriceTableRepository;
import br.com.libertsolutions.libertvendas.app.data.customer.CustomerApi;
import br.com.libertsolutions.libertvendas.app.data.customer.CustomerByIdSpecification;
import br.com.libertsolutions.libertvendas.app.data.customer.CustomerRepository;
import br.com.libertsolutions.libertvendas.app.data.order.OrderApi;
import br.com.libertsolutions.libertvendas.app.data.order.OrderByIdSpecification;
import br.com.libertsolutions.libertvendas.app.data.order.OrderRealmRepository;
import br.com.libertsolutions.libertvendas.app.data.order.OrderRepository;
import br.com.libertsolutions.libertvendas.app.data.order.OrdersByUserSpecification;
import br.com.libertsolutions.libertvendas.app.data.paymentmethod.PaymentMethodByIdSpecification;
import br.com.libertsolutions.libertvendas.app.data.paymentmethod.PaymentMethodRepository;
import br.com.libertsolutions.libertvendas.app.data.pricetable.PriceTableByIdSpecification;
import br.com.libertsolutions.libertvendas.app.data.pricetable.PriceTableRepository;
import br.com.libertsolutions.libertvendas.app.data.product.ProductRepository;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.domain.dto.OrderDto;
import br.com.libertsolutions.libertvendas.app.domain.dto.OrderItemDto;
import br.com.libertsolutions.libertvendas.app.domain.dto.ServerStatus;
import br.com.libertsolutions.libertvendas.app.domain.pojo.CompanyCustomer;
import br.com.libertsolutions.libertvendas.app.domain.pojo.CompanyPaymentMethod;
import br.com.libertsolutions.libertvendas.app.domain.pojo.CompanyPriceTable;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Customer;
import br.com.libertsolutions.libertvendas.app.domain.pojo.CustomerStatus;
import br.com.libertsolutions.libertvendas.app.domain.pojo.LoggedUser;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Order;
import br.com.libertsolutions.libertvendas.app.domain.pojo.OrderItem;
import br.com.libertsolutions.libertvendas.app.domain.pojo.OrderStatus;
import br.com.libertsolutions.libertvendas.app.domain.pojo.PaymentMethod;
import br.com.libertsolutions.libertvendas.app.domain.pojo.PriceTable;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Product;
import br.com.libertsolutions.libertvendas.app.presentation.orderlist.SyncOrdersEvent;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.OneoffTask;
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
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.provideCompanyPriceTableRepository;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.provideCustomerRepository;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.providePaymentMethodRepository;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.providePriceTableRepository;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.provideProductRepository;
import static br.com.libertsolutions.libertvendas.app.data.RemoteDataInjector.provideCustomerApi;
import static br.com.libertsolutions.libertvendas.app.data.RemoteDataInjector.provideOrderApi;
import static br.com.libertsolutions.libertvendas.app.data.RemoteDataInjector.providePaymentMethodApi;
import static br.com.libertsolutions.libertvendas.app.data.RemoteDataInjector.providePriceTableApi;
import static br.com.libertsolutions.libertvendas.app.data.RemoteDataInjector.provideProductApi;
import static br.com.libertsolutions.libertvendas.app.data.RemoteDataInjector.provideSyncApi;
import static br.com.libertsolutions.libertvendas.app.presentation.PresentationInjector.provideEventBus;
import static br.com.libertsolutions.libertvendas.app.presentation.PresentationInjector.provideSettingsRepository;
import static java.util.Collections.emptyList;

/**
 * @author Filipe Bezerra
 */
public class SyncTaskService extends GcmTaskService {

    private OrderApi orderApi;

    private OrderRepository orderRepository;

    private String companyCnpj;

    private String salesmanCpfOrCnpj;

    public static boolean schedule(@NonNull Context context, @IntRange(from = 0) int periodInMinutes) {
        try {
            final long syncPeriodInSeconds = TimeUnit.MINUTES.toSeconds(periodInMinutes);

            if (provideSettingsRepository().isRunningSyncWith(syncPeriodInSeconds)) {
                Timber.v("sync service already scheduled with period of %d minutes",
                        periodInMinutes);
                return false;
            }

            PeriodicTask periodicTask = new PeriodicTask.Builder()
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
                    .schedule(periodicTask);

            provideSettingsRepository().setRunningSyncWith(syncPeriodInSeconds);
            Timber.v("sync service scheduled with period of %d minutes", periodInMinutes);
            return true;
        } catch (Exception e) {
            Timber.e(e, "scheduling sync service failed");
            return false;
        }
    }

    public static boolean scheduleSingleSync(@NonNull Context context) {
        try {
            OneoffTask oneoffTask = new OneoffTask.Builder()
                    .setService(SyncTaskService.class)
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
                    //executed between 0 - 10s from now
                    .setExecutionWindow(0, 10)
                    .build();
            GcmNetworkManager
                    .getInstance(context.getApplicationContext())
                    .schedule(oneoffTask);

            Timber.v("single sync service scheduled within next 10 seconds");
            return true;
        } catch (Exception e) {
            Timber.e(e, "scheduling single sync service failed");
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
        restartFullSync();
    }

    @Override public int onRunTask(final TaskParams taskParams) {
        Timber.d("running sync service");

        final SettingsRepository settingsRepository = provideSettingsRepository();

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
        companyCnpj = loggedUser.getDefaultCompany().getCnpj();
        salesmanCpfOrCnpj = loggedUser.getSalesman().getCpfOrCnpj();

        SyncOrdersEvent syncOrdersEvent = provideEventBus().getStickyEvent(SyncOrdersEvent.class);
        if (syncOrdersEvent != null) {
            boolean successful = syncOrders(syncOrdersEvent.getOrders());
            if (successful) {
                provideEventBus().removeStickyEvent(SyncOrdersEvent.class);
                restartFullSync();
                return GcmNetworkManager.RESULT_SUCCESS;
            } else {
                return GcmNetworkManager.RESULT_RESCHEDULE;
            }
        }

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

            final List<Order> createdOrModifiedOrders = getOrderRepository()
                    .query(new OrdersByUserSpecification(salesmanId, companyId)
                            .byStatusCreatedOrModified())
                    .toBlocking()
                    .firstOrDefault(Collections.emptyList());

            boolean successful = syncOrders(createdOrModifiedOrders);
            if (!successful)
                return GcmNetworkManager.RESULT_RESCHEDULE;
        } else {
            Timber.i("Orders are not enabled to sync automatically");
        }
        //endregion

        //region getting customer updates
        try {
            final Response<List<Customer>> response = customerApi
                    .getUpdates(companyCnpj, lastSyncTime)
                    .execute();

            if (response.isSuccessful()) {
                final List<Customer> updatedCustomers = response.body();

                if (!updatedCustomers.isEmpty()) {
                    final CompanyCustomerRepository companyCustomerRepository
                            = provideCompanyCustomerRepository();

                    for (final Customer customer : updatedCustomers) {
                        final Integer customerId = customer.getCustomerId();
                        final Customer existingCustomer = customerRepository
                                .findFirst(new CustomerByIdSpecification(customerId))
                                .toBlocking()
                                .singleOrDefault(null);

                        if (existingCustomer != null) {
                            customer
                                    .withId(existingCustomer.getId())
                                    .withStatus(existingCustomer.getStatus());

                            customerRepository
                                    .save(customer)
                                    .toBlocking()
                                    .single();
                        } else {
                            final Customer savedCustomer = customerRepository
                                    .save(customer)
                                    .toBlocking()
                                    .single();

                            final CompanyCustomer newCompanyCustomer = CompanyCustomer
                                    .from(loggedUser.getDefaultCompany(), savedCustomer);

                            companyCustomerRepository
                                    .save(newCompanyCustomer)
                                    .toBlocking()
                                    .single();
                        }
                    }
                }
            } else {
                Timber.i("Unsuccessful getting customer updates. %s", response.message());
            }
        } catch (IOException e) {
            Timber.e(e, "Server failure while getting customer updates");
            return GcmNetworkManager.RESULT_RESCHEDULE;
        } catch (RuntimeException e) {
            Timber.e(e, "Unknown error while getting customer updates");
            return GcmNetworkManager.RESULT_RESCHEDULE;
        }
        //endregion

        //region getting orders updates
        try {
            final Response<List<OrderDto>> response = getOrderApi()
                    .getUpdates(companyCnpj, salesmanCpfOrCnpj, lastSyncTime)
                    .execute();

            if (response.isSuccessful()) {
                final List<OrderDto> updatedOrders = response.body();

                if (!updatedOrders.isEmpty()) {
                    for (final OrderDto order : updatedOrders) {
                        final int orderId = order.orderId;
                        final Order existingOrder = getOrderRepository()
                                .findFirst(new OrderByIdSpecification(orderId))
                                .toBlocking()
                                .singleOrDefault(null);

                        if (existingOrder != null) {
                            if (order.status == 3) {
                                existingOrder.withStatus(OrderStatus.STATUS_CANCELLED);
                            }
                            existingOrder
                                    .withLastChangeTime(order.lastChangeTime);

                            getOrderRepository()
                                    .save(existingOrder)
                                    .toBlocking()
                                    .single();
                        }
                    }
                }
            } else {
                Timber.i("Unsuccessful getting order updates. %s", response.message());
            }
        } catch (IOException e) {
            Timber.e(e, "Server failure while getting order updates");
            return GcmNetworkManager.RESULT_RESCHEDULE;
        } catch (RuntimeException e) {
            Timber.e(e, "Unknown error while getting order updates");
            return GcmNetworkManager.RESULT_RESCHEDULE;
        }
        //endregion

        //region getting payment method updates
        try {
            final Response<List<PaymentMethod>> response = providePaymentMethodApi()
                    .getUpdates(companyCnpj, lastSyncTime)
                    .execute();

            if (response.isSuccessful()) {
                final List<PaymentMethod> updatedPaymentMethods = response.body();

                if (!updatedPaymentMethods.isEmpty()) {
                    final PaymentMethodRepository paymentMethodRepository
                            = providePaymentMethodRepository();
                    final CompanyPaymentMethodRepository companyPaymentMethodRepository
                            = provideCompanyPaymentMethodRepository();

                    for (final PaymentMethod paymentMethod : updatedPaymentMethods) {
                        final Integer paymentMethodId = paymentMethod.getPaymentMethodId();
                        final PaymentMethod existingPaymentMethod = paymentMethodRepository
                                .findFirst(new PaymentMethodByIdSpecification(paymentMethodId))
                                .toBlocking()
                                .singleOrDefault(null);

                        final PaymentMethod savedPaymentMethod = paymentMethodRepository
                                .save(paymentMethod)
                                .toBlocking()
                                .single();

                        if (existingPaymentMethod == null) {
                            final CompanyPaymentMethod newCompanyPaymentMethod
                                    = CompanyPaymentMethod
                                    .from(loggedUser.getDefaultCompany(), savedPaymentMethod);

                            companyPaymentMethodRepository
                                    .save(newCompanyPaymentMethod)
                                    .toBlocking()
                                    .single();
                        }
                    }
                }
            } else {
                Timber.i("Unsuccessful getting payment method updates. %s", response.message());
            }
        } catch (IOException e) {
            Timber.e(e, "Server failure while getting payment method updates");
            return GcmNetworkManager.RESULT_RESCHEDULE;
        } catch (RuntimeException e) {
            Timber.e(e, "Unknown error while getting payment method updates");
            return GcmNetworkManager.RESULT_RESCHEDULE;
        }
        //endregion

        //region getting price table updates
        try {
            final Response<List<PriceTable>> response = providePriceTableApi()
                    .getUpdates(companyCnpj, lastSyncTime)
                    .execute();

            if (response.isSuccessful()) {
                final List<PriceTable> updatedPriceTables = response.body();

                if (!updatedPriceTables.isEmpty()) {
                    final PriceTableRepository priceTableRepository = providePriceTableRepository();
                    final CompanyPriceTableRepository companyPriceTableRepository
                            = provideCompanyPriceTableRepository();

                    for (final PriceTable priceTable : updatedPriceTables) {
                        final Integer priceTableId = priceTable.getPriceTableId();
                        final PriceTable existingPriceTable = priceTableRepository
                                .findFirst(new PriceTableByIdSpecification(priceTableId))
                                .toBlocking()
                                .singleOrDefault(null);

                        final PriceTable savedPriceTable = priceTableRepository
                                .save(priceTable)
                                .toBlocking()
                                .single();

                        if (existingPriceTable == null) {
                            final CompanyPriceTable newCompanyPriceTable = CompanyPriceTable
                                    .from(loggedUser.getDefaultCompany(), savedPriceTable);

                            companyPriceTableRepository
                                    .save(newCompanyPriceTable)
                                    .toBlocking()
                                    .single();
                        }
                    }
                }
            } else {
                Timber.i("Unsuccessful getting price table updates. %s", response.message());
            }
        } catch (IOException e) {
            Timber.e(e, "Server failure while getting price table updates");
            return GcmNetworkManager.RESULT_RESCHEDULE;
        } catch (RuntimeException e) {
            Timber.e(e, "Unknown error while getting price table updates");
            return GcmNetworkManager.RESULT_RESCHEDULE;
        }
        //endregion

        //region getting product updates
        try {
            final Response<List<Product>> response = provideProductApi()
                    .getUpdates(companyCnpj, lastSyncTime)
                    .execute();

            if (response.isSuccessful()) {
                final List<Product> updatedProducts = response.body();

                if (!updatedProducts.isEmpty()) {
                    final ProductRepository productRepository = provideProductRepository();

                    for (final Product product : updatedProducts) {
                        productRepository
                                .save(product)
                                .toBlocking()
                                .single();
                    }
                }
            } else {
                Timber.i("Unsuccessful getting product updates. %s", response.message());
            }
        } catch (IOException e) {
            Timber.e(e, "Server failure while getting product updates");
            return GcmNetworkManager.RESULT_RESCHEDULE;
        } catch (RuntimeException e) {
            Timber.e(e, "Unknown error while getting product updates");
            return GcmNetworkManager.RESULT_RESCHEDULE;
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
            return GcmNetworkManager.RESULT_RESCHEDULE;
        } catch (RuntimeException e) {
            Timber.e(e, "Unknown error while getting server status");
            return GcmNetworkManager.RESULT_RESCHEDULE;
        }
        //endregion

        return GcmNetworkManager.RESULT_SUCCESS;
    }

    private boolean syncOrders(List<Order> orders) {
        if (orders != null && !orders.isEmpty()) {
            for (Order order : orders) {
                try {
                    final OrderDto postOrder = order.createPostOrder();

                    final Response<OrderDto> response = getOrderApi()
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

                        getOrderRepository()
                                .save(order)
                                .toBlocking()
                                .first();
                    } else {
                        Timber.i("Unsuccessful orders sync. %s", response.message());
                    }
                } catch (IOException e) {
                    Timber.e(e, "Server failure while syncing orders");
                    return false;
                } catch (RuntimeException e) {
                    Timber.e(e, "Unknown error while syncing orders");
                    return false;
                }
            }
        }
        return true;
    }

    private OrderApi getOrderApi() {
        if (orderApi == null) {
            orderApi = provideOrderApi();
        }
        return orderApi;
    }

    private OrderRepository getOrderRepository() {
        if (orderRepository == null) {
            orderRepository = new OrderRealmRepository();
        }
        return orderRepository;
    }

    private void restartFullSync() {
        SyncTaskService.cancelAll(this);
        SyncTaskService.schedule(this,
                provideSettingsRepository().getSettings().getSyncPeriodicity());
    }
}
