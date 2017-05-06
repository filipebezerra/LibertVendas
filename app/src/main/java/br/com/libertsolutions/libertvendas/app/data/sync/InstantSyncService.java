package br.com.libertsolutions.libertvendas.app.data.sync;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import br.com.libertsolutions.libertvendas.app.data.customer.CustomerApi;
import br.com.libertsolutions.libertvendas.app.data.customer.CustomerRepository;
import br.com.libertsolutions.libertvendas.app.data.order.OrderApi;
import br.com.libertsolutions.libertvendas.app.data.order.OrderRepository;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.domain.dto.OrderDto;
import br.com.libertsolutions.libertvendas.app.domain.dto.OrderItemDto;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Customer;
import br.com.libertsolutions.libertvendas.app.domain.pojo.CustomerStatus;
import br.com.libertsolutions.libertvendas.app.domain.pojo.LoggedUser;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Order;
import br.com.libertsolutions.libertvendas.app.domain.pojo.OrderItem;
import br.com.libertsolutions.libertvendas.app.domain.pojo.OrderStatus;
import br.com.libertsolutions.libertvendas.app.presentation.orderlist.SyncOrdersEvent;
import java.io.IOException;
import java.util.List;
import retrofit2.Response;
import timber.log.Timber;

import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.provideCustomerRepository;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.providerOrderRepository;
import static br.com.libertsolutions.libertvendas.app.data.RemoteDataInjector.provideCustomerApi;
import static br.com.libertsolutions.libertvendas.app.data.RemoteDataInjector.provideOrderApi;
import static br.com.libertsolutions.libertvendas.app.data.sync.OrdersSyncedEvent.ordersInstantlySynced;
import static br.com.libertsolutions.libertvendas.app.presentation.PresentationInjector.provideEventBus;
import static br.com.libertsolutions.libertvendas.app.presentation.PresentationInjector.provideSettingsRepository;
import static java.util.Collections.singletonList;

/**
 * @author Filipe Bezerra
 */
public class InstantSyncService extends IntentService {

    private static final String TAG = InstantSyncService.class.getSimpleName();

    public static void execute(@NonNull Context context) {
        context.startService(new Intent(context, InstantSyncService.class));
    }

    private SettingsRepository settingsRepository;

    private CustomerRepository customerRepository;

    private CustomerApi customerApi;

    private LoggedUser loggedUser;

    public InstantSyncService() {
        super(TAG);
    }

    @Override protected void onHandleIntent(@Nullable final Intent intent) {
        Timber.d("running instant sync service");

        boolean notifyOrderUpdates = false;
        boolean syncCancelled = false;

        if (!getSettingsRepository().isUserLoggedIn()) {
            Timber.i("No user logged, sync will not be executed");
        } else {
            syncCancelled = SyncTaskService.cancelAll(this);
            notifyOrderUpdates = syncOrders();
        }

        provideEventBus().removeStickyEvent(SyncOrdersEvent.class);
        if (notifyOrderUpdates) {
            provideEventBus().post(ordersInstantlySynced());
        }

        if (syncCancelled) {
            SyncTaskService.schedule(this,
                    getSettingsRepository().getSettings().getSyncPeriodicity());
        }
    }

    private boolean syncOrders() {
        SyncOrdersEvent syncOrdersEvent = provideEventBus().getStickyEvent(SyncOrdersEvent.class);
        if (syncOrdersEvent == null)
            return false;

        List<Order> orders = syncOrdersEvent.getOrders();
        if (orders == null || orders.isEmpty())
            return false;

        final String salesmanCpfOrCnpj = getLoggedUser().getSalesman().getCpfOrCnpj();
        final OrderApi orderApi = provideOrderApi();
        final OrderRepository orderRepository = providerOrderRepository();

        boolean anyOrderSynced = false;

        for (Order order : orders) {
            final Customer customer = order.getCustomer();

            if (customer.getStatus() == CustomerStatus.STATUS_CREATED) {
                if (!syncNewCustomer(customer))
                    continue;
            } else if (customer.getStatus() == CustomerStatus.STATUS_MODIFIED) {
                if (!syncModifiedCustomer(customer))
                    continue;
            }

            try {
                final OrderDto postOrder = order.createPostOrder();

                final Response<OrderDto> response = orderApi
                        .createOrder(getCompanyCnpj(), salesmanCpfOrCnpj, postOrder)
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
                    anyOrderSynced = true;
                } else {
                    Timber.e("Unsuccessful orders sync. %s", response.message());
                }
            } catch (IOException e) {
                Timber.e(e, "Server failure while syncing orders");
            } catch (RuntimeException e) {
                Timber.e(e, "Unknown error while syncing orders");
            }
        }
        return anyOrderSynced;
    }

    private boolean syncNewCustomer(Customer newCustomer) {
        try {
            final Response<Customer> response = getCustomerApi()
                    .createCustomer(getCompanyCnpj(), newCustomer)
                    .execute();

            if (response.isSuccessful()) {
                getCustomerRepository()
                        .save(response.body())
                        .toBlocking()
                        .single();
                return true;
            } else {
                Timber.e("Unsuccessful new customer sync. %s", response.message());
                return false;
            }
        } catch (IOException e) {
            Timber.e(e, "Server failure while syncing new customer");
            return false;
        } catch (RuntimeException e) {
            Timber.e(e, "Unknown error while syncing new customer");
            return false;
        }
    }

    private boolean syncModifiedCustomer(Customer modifiedCustomer) {
        try {
            Response<List<Customer>> response = getCustomerApi()
                    .updateCustomers(getCompanyCnpj(), singletonList(modifiedCustomer))
                    .execute();

            if (response.isSuccessful()) {
                getCustomerRepository()
                        .save(response.body())
                        .toBlocking()
                        .single();
                return true;
            } else {
                Timber.i("Unsuccessful update customer sync. %s", response.message());
                return false;
            }
        } catch (IOException e) {
            Timber.e(e, "Server failure while syncing modified customer");
            return false;
        } catch (RuntimeException e) {
            Timber.e(e, "Unknown error while syncing modified customer");
            return false;
        }
    }

    private LoggedUser getLoggedUser() {
        if (loggedUser == null) {
            loggedUser = getSettingsRepository()
                    .getLoggedUser()
                    .toBlocking()
                    .single();
        }
        return loggedUser;
    }

    private SettingsRepository getSettingsRepository() {
        if (settingsRepository == null) {
            settingsRepository = provideSettingsRepository();
        }
        return settingsRepository;
    }

    private String getCompanyCnpj() {
        return getLoggedUser().getDefaultCompany().getCnpj();
    }

    private CustomerApi getCustomerApi() {
        if (customerApi == null) {
            customerApi = provideCustomerApi();
        }
        return customerApi;
    }

    private CustomerRepository getCustomerRepository() {
        if (customerRepository == null) {
            customerRepository = provideCustomerRepository();
        }
        return customerRepository;
    }
}
