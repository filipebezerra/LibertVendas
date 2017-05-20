package br.com.libertsolutions.libertvendas.app.presentation.addorder.orderitems;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.data.pricetable.PriceTableByIdSpecification;
import br.com.libertsolutions.libertvendas.app.data.pricetable.PriceTableRepository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Customer;
import br.com.libertsolutions.libertvendas.app.domain.pojo.LoggedUser;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Order;
import br.com.libertsolutions.libertvendas.app.domain.pojo.OrderItem;
import br.com.libertsolutions.libertvendas.app.domain.pojo.PriceTable;
import br.com.libertsolutions.libertvendas.app.domain.pojo.PriceTableItem;
import br.com.libertsolutions.libertvendas.app.presentation.base.BaseFragment;
import br.com.libertsolutions.libertvendas.app.presentation.customerlist.SelectedCustomerEvent;
import br.com.libertsolutions.libertvendas.app.presentation.main.LoggedInUserEvent;
import br.com.libertsolutions.libertvendas.app.presentation.orderlist.SelectedOrderEvent;
import butterknife.BindView;
import butterknife.OnClick;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.greenrobot.eventbus.Subscribe;
import rx.Subscriber;
import rx.Subscription;
import timber.log.Timber;

import static android.R.color.white;
import static android.support.design.widget.Snackbar.LENGTH_SHORT;
import static android.text.TextUtils.isEmpty;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static br.com.libertsolutions.libertvendas.app.R.color.colorPrimary;
import static br.com.libertsolutions.libertvendas.app.R.id.button_all_retry;
import static br.com.libertsolutions.libertvendas.app.R.id.recycler_view_order_items;
import static br.com.libertsolutions.libertvendas.app.R.id.swipe_container_all_pull_refresh;
import static br.com.libertsolutions.libertvendas.app.R.layout.fragment_select_order_items;
import static br.com.libertsolutions.libertvendas.app.R.string.select_order_items_invalid_quantity;
import static br.com.libertsolutions.libertvendas.app.R.string.select_order_items_no_order_items;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.providePriceTableRepository;
import static br.com.libertsolutions.libertvendas.app.presentation.addorder.orderitems.AddedOrderItemsEvent.newEvent;
import static java.lang.Integer.valueOf;
import static java.util.Collections.emptyList;
import static rx.android.schedulers.AndroidSchedulers.mainThread;
import static rx.schedulers.Schedulers.io;

/**
 * @author Filipe Bezerra
 */
public class SelectOrderItemsStepFragment extends BaseFragment implements Step,
        SelectOrderItemsCallbacks {

    private PriceTableRepository priceTableRepository;

    private Subscription currentSubscription;

    private SelectOrderItemsAdapter selectOrderItemsAdapter;

    private OnGlobalLayoutListener recyclerViewLayoutListener = null;

    private LoggedUser loggedUser;

    private Integer customerDefaultPriceTable;

    private List<OrderItem> orderItems;

    private Set<OrderItem> selectedOrderItems;

    private Order selectedOrder;

    private SearchView searchView;

    @BindView(swipe_container_all_pull_refresh) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(recycler_view_order_items) RecyclerView recyclerViewOrderItems;

    public static SelectOrderItemsStepFragment newInstance() {
        return new SelectOrderItemsStepFragment();
    }

    @Override protected int provideContentViewResource() {
        return fragment_select_order_items;
    }

    @Override public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        SelectedOrderEvent event = eventBus().getStickyEvent(SelectedOrderEvent.class);
        if (event != null) {
            selectedOrder = event.getOrder();
        }
    }

    @Nullable @Override public View onCreateView(final LayoutInflater inflater,
            @Nullable final ViewGroup container, @Nullable final Bundle inState) {
        final View view = super.onCreateView(inflater, container, inState);

        recyclerViewOrderItems.setHasFixedSize(true);

        swipeRefreshLayout.setColorSchemeResources(colorPrimary);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(white);

        priceTableRepository = providePriceTableRepository();

        return view;
    }

    @Override public void onViewCreated(final View view,
            @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadProducts();
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_all_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(R.string.select_order_items_search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override public boolean onQueryTextChange(String newText) {
                if (selectOrderItemsAdapter != null) {
                    selectOrderItemsAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
    }

    @Override public void onPrepareOptionsMenu(final Menu menu) {
        menu.findItem(R.id.action_all_search).setVisible(
                selectOrderItemsAdapter != null && !selectOrderItemsAdapter.isEmptyList());
    }

    @OnClick(button_all_retry) void onButtonRetryClicked() {
        mLinearLayoutErrorState.setVisibility(GONE);
        loadProducts();
    }

    @Subscribe(sticky = true) public void onLoggedInUser(LoggedInUserEvent event) {
        if (loggedUser != null && !loggedUser.equals(event.getUser())) {
            loggedUser = event.getUser();
            loadProducts();
        }
    }

    @Subscribe(priority = 1) public void onSelectedCustomer(SelectedCustomerEvent event) {
        Customer customer = event.getCustomer();
        if (!isEmpty(customer.getDefaultPriceTable()) &&
                !isEmpty(customer.getDefaultPriceTable().trim())) {
            customerDefaultPriceTable = valueOf(customer.getDefaultPriceTable());
            loadProducts();
        }
    }

    @Override public void onAddOrderItemRequested(final OrderItem orderItem, final int position) {
        final float quantity = orderItem.getQuantity() + 1;
        if (checkQuantity(quantity)) {
            orderItem.addQuantity(1);
            selectOrderItemsAdapter.notifyItemChanged(position);
            selectedOrderItems.add(orderItem);
        }
    }

    @Override public void onRemoveOrderItemRequested(
            final OrderItem orderItem, final int position) {
        final Float quantity = orderItem.getQuantity();
        if (quantity != null && quantity >= 1) {
            orderItem.removeOneFromQuantity();
            selectOrderItemsAdapter.notifyItemChanged(position);
            if (orderItem.getQuantity() == 0)
                selectedOrderItems.remove(orderItem);
        }
    }

    @Override public void onChangeOrderItemQuantityRequested(
            final OrderItem orderItem, final float quantity, final int position) {
        if (checkQuantity(quantity)) {
            orderItem.withQuantity(quantity);
            selectOrderItemsAdapter.notifyItemChanged(position);
            if (orderItem.getQuantity() == 0)
                selectedOrderItems.remove(orderItem);
            else
                selectedOrderItems.add(orderItem);
        }
    }

    @Override public VerificationError verifyStep() {
        if (!isEmpty(searchView.getQuery())) {
            searchView.setQuery("", false);
            searchView.clearFocus();
            selectOrderItemsAdapter.getFilter().filter("");
        }

        List<OrderItem> addedOrderItems = getSelectedOrderItems();
        if (!addedOrderItems.isEmpty()) {
            eventBus().post(newEvent(addedOrderItems));
            return null;
        } else {
            return new VerificationError(getString(select_order_items_no_order_items));
        }
    }

    @Override public void onSelected() {}

    @Override public void onError(@NonNull final VerificationError error) {
        Snackbar.make(getView(), error.getErrorMessage(), LENGTH_SHORT).show();
    }

    private void loadProducts() {
        currentSubscription = priceTableRepository
                .findFirst(new PriceTableByIdSpecification(loadDefaultPriceTableId()))
                .observeOn(mainThread())
                .doOnNext(priceTable ->
                        eventBus().post(SelectedPriceTableEvent.newEvent(priceTable)))
                .observeOn(io())
                .map(PriceTable::getItems)
                .flatMapIterable(item -> item)
                .map(this::createOrderItem)
                .observeOn(mainThread())
                .doOnUnsubscribe(() -> swipeRefreshLayout.setRefreshing(false))
                .subscribe(createPriceTableItemSubscriber());
    }

    private int loadDefaultPriceTableId() {
        if (loggedUser == null) {
            loggedUser = eventBus().getStickyEvent(LoggedInUserEvent.class).getUser();
        }

        if (customerDefaultPriceTable != null) {
            return customerDefaultPriceTable;
        }

        return loggedUser.getDefaultCompany().getPriceTableId();
    }

    private OrderItem createOrderItem(final PriceTableItem priceTableItem) {
        OrderItem orderItem;
        if (selectedOrder != null) {
            for (final OrderItem item : selectedOrder.getItems()) {
                if (item.getItem().equals(priceTableItem)) {
                    orderItem = new OrderItem()
                            .withTempId(UUID.randomUUID().toString())
                            .withItem(priceTableItem)
                            .withQuantity(item.getQuantity());

                    if (selectedOrderItems == null)
                        selectedOrderItems = new LinkedHashSet<>();
                    selectedOrderItems.add(orderItem);
                    return orderItem;
                }
            }
        }

        orderItem = new OrderItem()
                .withTempId(UUID.randomUUID().toString())
                .withItem(priceTableItem)
                .withQuantity(0.0f);
        return orderItem;
    }

    private Subscriber<OrderItem> createPriceTableItemSubscriber() {
        return new Subscriber<OrderItem>() {
            @Override public void onStart() {
                startLoadingProducts();
            }

            @Override public void onError(final Throwable e) {
                handleLoadProductsError(e);
            }

            @Override public void onNext(final OrderItem orderItem) {
                orderItems.add(orderItem);
            }

            @Override public void onCompleted() {
                showOrderItems();
            }
        };
    }

    private void startLoadingProducts() {
        if (orderItems == null) {
            orderItems = new ArrayList<>();
            selectedOrderItems = new LinkedHashSet<>();
        } else {
            orderItems.clear();
            selectedOrderItems.clear();
        }
        swipeRefreshLayout.setRefreshing(true);
        selectOrderItemsAdapter = null;
        recyclerViewOrderItems.setAdapter(null);
        recyclerViewOrderItems.setVisibility(GONE);
        mLinearLayoutEmptyState.setVisibility(VISIBLE);
        getActivity().invalidateOptionsMenu();
    }

    private void handleLoadProductsError(Throwable e) {
        Timber.e(e, "Could not load products");
        swipeRefreshLayout.setRefreshing(false);
        mLinearLayoutErrorState.setVisibility(VISIBLE);
        mLinearLayoutEmptyState.setVisibility(GONE);
    }

    private void showOrderItems() {
        if (!orderItems.isEmpty()) {
            recyclerViewOrderItems.setVisibility(VISIBLE);
            recyclerViewOrderItems.setAdapter(
                    selectOrderItemsAdapter = new SelectOrderItemsAdapter(
                            orderItems, this, getContext()));
            recyclerViewOrderItems
                    .getViewTreeObserver()
                    .addOnGlobalLayoutListener(
                            recyclerViewLayoutListener = this::onRecyclerViewFinishLoading);
        } else {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void onRecyclerViewFinishLoading() {
        if (getView() != null) {
            recyclerViewOrderItems
                    .getViewTreeObserver()
                    .removeOnGlobalLayoutListener(recyclerViewLayoutListener);
            recyclerViewLayoutListener = null;

            getActivity().invalidateOptionsMenu();
            swipeRefreshLayout.setRefreshing(false);
            swipeRefreshLayout.setEnabled(false);
            mLinearLayoutEmptyState.setVisibility(GONE);
            if (!eventBus().isRegistered(this)) {
                eventBus().register(this);
            }
        }
    }

    private boolean checkQuantity(final float quantity) {
        if (quantity < 0) {
            Snackbar.make(getView(), select_order_items_invalid_quantity, LENGTH_SHORT)
                    .show();
            return false;
        }

        return true;
    }

    private List<OrderItem> getSelectedOrderItems() {
        if (selectedOrderItems == null || selectedOrderItems.isEmpty()) {
            return emptyList();
        }

        List<OrderItem> orderItems = new ArrayList<>();
        for (final OrderItem item : selectedOrderItems) {
            if (item.getQuantity() > 0) {
                orderItems.add(item);
            }
        }
        return orderItems;
    }

    @Override public void onDestroyView() {
        if (currentSubscription != null && !currentSubscription.isUnsubscribed()) {
            currentSubscription.unsubscribe();
        }
        eventBus().unregister(this);
        super.onDestroyView();
    }
}
