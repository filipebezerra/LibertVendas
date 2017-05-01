package br.com.libertsolutions.libertvendas.app.presentation.orderlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.view.ActionMode;
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
import br.com.libertsolutions.libertvendas.app.data.order.OrderRepository;
import br.com.libertsolutions.libertvendas.app.data.order.OrdersByUserSpecification;
import br.com.libertsolutions.libertvendas.app.data.sync.OrdersSyncedEvent;
import br.com.libertsolutions.libertvendas.app.data.sync.SyncTaskService;
import br.com.libertsolutions.libertvendas.app.domain.pojo.LoggedUser;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Order;
import br.com.libertsolutions.libertvendas.app.domain.pojo.OrderStatus;
import br.com.libertsolutions.libertvendas.app.presentation.addorder.orderform.SavedOrderEvent;
import br.com.libertsolutions.libertvendas.app.presentation.base.BaseFragment;
import br.com.libertsolutions.libertvendas.app.presentation.main.LoggedInUserEvent;
import br.com.libertsolutions.libertvendas.app.presentation.util.EventTracker;
import butterknife.BindView;
import butterknife.OnClick;
import com.mikepenz.fastadapter.FastAdapter.OnClickListener;
import com.mikepenz.fastadapter.FastAdapter.OnLongClickListener;
import com.mikepenz.fastadapter.IItemAdapter.Predicate;
import com.mikepenz.fastadapter.ISelectionListener;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter_extensions.ActionModeHelper;
import com.mikepenz.materialize.util.UIUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.greenrobot.eventbus.Subscribe;
import rx.Subscriber;
import rx.Subscription;
import timber.log.Timber;

import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.providerOrderRepository;
import static br.com.libertsolutions.libertvendas.app.presentation.orderlist.SelectedOrderEvent.duplicateOrder;
import static br.com.libertsolutions.libertvendas.app.presentation.orderlist.SelectedOrderEvent.selectOrder;
import static br.com.libertsolutions.libertvendas.app.presentation.util.EventTracker.ACTION_DUPLICATE_ORDER;
import static br.com.libertsolutions.libertvendas.app.presentation.util.EventTracker.ACTION_MANUAL_SYNC;
import static br.com.libertsolutions.libertvendas.app.presentation.util.EventTracker.SEARCHED_ORDERS;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * @author Filipe Bezerra
 */
public class OrderListFragment extends BaseFragment implements OnRefreshListener {

    private static final String ARG_SHOW_ONLY_PENDING_ORDERS =
            OrderListFragment.class.getName() + ".argShowOnlyPendingOrders";

    private OrderRepository orderRepository;

    private Subscription currentSubscription;

    private FastItemAdapter<OrderAdapterItem> fastItemAdapter;

    private ActionModeHelper actionModeHelper;

    private Predicate<OrderAdapterItem> filterPredicate = (item, constraint) ->
            !item.getOrder().getCustomer().getName().trim().toLowerCase()
                    .contains(constraint.toString().toLowerCase());

    private OnClickListener<OrderAdapterItem> preClickListener = (v, a, item, position) -> {
        if (actionModeHelper.isActive()) {
            Set<OrderAdapterItem> selectedItems = fastItemAdapter.getSelectedItems();
            OrderAdapterItem[] selectedItemsArray =
                    selectedItems.toArray(new OrderAdapterItem[selectedItems.size()]);

            Order firstOrder = selectedItemsArray[0].getOrder();
            Order selectedOrder = item.getOrder();

            if (!selectedOrder.isStatusEquals(firstOrder)) {
                Snackbar.make(getView(), R.string.order_list_multi_selection_only_for_same_status,
                        Snackbar.LENGTH_SHORT)
                        .show();
                return true;
            } else if (selectedOrder.isStatusSyncedOrCancelled()) {
                if (selectedItems.contains(item) && selectedItems.size() == 1) {
                    actionModeHelper.reset();
                } else {
                    Snackbar.make(getView(), R.string.order_list_single_selection_to_duplicate_order,
                            Snackbar.LENGTH_SHORT)
                            .show();
                }
                return true;
            }
        }

        return actionModeHelper.onClick(item) != null;
    };

    private OnClickListener<OrderAdapterItem> clickListener = (v, a, i, position) -> {
        if (!actionModeHelper.isActive()) {
            final Order selectedOrder = fastItemAdapter.getAdapterItem(position)
                    .getOrder();

            if (selectedOrder != null) {
                eventBus().postSticky(selectOrder(selectedOrder));

                if (selectedOrder.getStatus() == OrderStatus.STATUS_SYNCED ||
                        selectedOrder.getStatus() == OrderStatus.STATUS_CANCELLED) {
                    navigate().toViewOrder();
                } else {
                    navigate().toAddOrder();
                }
            }
        } else {
            actionModeHelper.getActionMode().invalidate();
        }

        return false;
    };

    private OnLongClickListener<OrderAdapterItem> preLongClickListener = (v, d, i, position) -> {
        ActionMode actionMode = actionModeHelper.onLongClick(getHostActivity(), position);

        if (actionMode != null) {
            //we want color our CAB
            getHostActivity()
                    .findViewById(R.id.action_mode_bar)
                    .setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(getContext(),
                            R.attr.colorPrimary, R.color.material_drawer_primary));
        }

        return actionMode != null;
    };

    private ISelectionListener<OrderAdapterItem> seleciontListener = (item, selected) -> {
        if (actionModeHelper.isActive()) {
            actionModeHelper.getActionMode().invalidate();
        }
    };

    protected OnGlobalLayoutListener recyclerViewLayoutListener = null;

    protected LoggedUser loggedUser;

    @BindView(R.id.swipe_container_all_pull_refresh) protected SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recycler_view_orders) protected RecyclerView recyclerViewOrders;

    public static OrderListFragment newInstance(boolean showOnlyPendingOrders) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_SHOW_ONLY_PENDING_ORDERS, showOnlyPendingOrders);
        fragment.setArguments(args);
        return fragment;
    }

    @Override protected int provideContentViewResource() {
        return R.layout.fragment_order_list;
    }

    @Override public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable @Override public View onCreateView(
            final LayoutInflater inflater, @Nullable final ViewGroup container,
            @Nullable final Bundle inState) {
        View view = super.onCreateView(inflater, container, inState);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);

        orderRepository = providerOrderRepository();

        fastItemAdapter = new FastItemAdapter<>();
        actionModeHelper = new ActionModeHelper(
                fastItemAdapter, R.menu.menu_cab_order_list, new ActionBarCallback());

        fastItemAdapter.setHasStableIds(true);
        fastItemAdapter
                .withFilterPredicate(filterPredicate)
                .withSelectable(true)
                .withMultiSelect(true)
                .withSelectOnLongClick(true)
                .withSavedInstanceState(inState)
                .withOnPreClickListener(preClickListener)
                .withOnClickListener(clickListener)
                .withOnPreLongClickListener(preLongClickListener)
                .withSelectionListener(seleciontListener);

        recyclerViewOrders.setHasFixedSize(true);

        return view;
    }

    @Override public boolean onOptionsItemSelected(final MenuItem item) {
        return getHostActivity().onOptionsItemSelected(item);
    }

    @Override public void onViewCreated(
            final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadOrders();
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_all_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(R.string.order_list_search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) {
                EventTracker.search(SEARCHED_ORDERS, query);
                fastItemAdapter.filter(query);
                return false;
            }

            @Override public boolean onQueryTextChange(String query) {
                fastItemAdapter.filter(query);
                return true;
            }
        });
    }

    @Override public void onPrepareOptionsMenu(final Menu menu) {
        menu.findItem(R.id.action_all_search).setVisible(recyclerViewOrders.getAdapter() != null);
    }

    @Override public void onRefresh() {
        loadOrders();
    }

    @OnClick(R.id.button_all_retry) void onButtonRetryClicked() {
        mLinearLayoutErrorState.setVisibility(View.GONE);
        loadOrders();
    }

    @Subscribe(sticky = true) public void onLoggedInUserEvent(LoggedInUserEvent event) {
        if (loggedUser != null && !loggedUser.equals(event.getUser())) {
            loggedUser = event.getUser();
            loadOrders();
        }
    }

    @Subscribe(sticky = true) public void onSavedOrder(SavedOrderEvent event) {
        final Order order = event.getOrder();
        eventBus().removeStickyEvent(SavedOrderEvent.class);
        //TODO: maybe reload the list, this is already accomplished by onLoggedInUserEvent, but the better way is to do that way
/*
        if (mOrderListAdapter != null) {
            final int position = mOrderListAdapter.updateOrder(order);
            if (position != NO_POSITION) {
                recyclerViewOrders.scrollToPosition(position);
            }
        } else {
            showOrders(new ArrayList<>(Collections.singletonList(order)));
        }
*/
    }

    @Subscribe public void onOrdersSynced(OrdersSyncedEvent event) {
        getActivity().runOnUiThread(this::loadOrders);
    }

    private void loadOrders() {
        OrdersByUserSpecification specification
                = new OrdersByUserSpecification(getSalesmanId(), getCompanyId())
                .orderByIssueDate();

        if (isShowOnlyPendingOrders()) {
            specification.byStatusCreatedOrModified();
        }

        currentSubscription = orderRepository
                .query(specification)
                .observeOn(mainThread())
                .subscribe(createOrderListSubscriber());
    }

    private int getSalesmanId() {
        return getLoggedUser().getSalesman().getSalesmanId();
    }

    private int getCompanyId() {
        return getLoggedUser().getDefaultCompany().getCompanyId();
    }

    private LoggedUser getLoggedUser() {
        if (loggedUser == null) {
            loggedUser = eventBus().getStickyEvent(LoggedInUserEvent.class).getUser();
        }
        return loggedUser;
    }

    private boolean isShowOnlyPendingOrders() {
        return getArguments() != null &&
                getArguments().getBoolean(ARG_SHOW_ONLY_PENDING_ORDERS, false);
    }

    private Subscriber<List<Order>> createOrderListSubscriber() {
        return new Subscriber<List<Order>>() {
            @Override public void onStart() {
                startLoadingOrders();
            }

            @Override public void onError(final Throwable e) {
                handleLoadOrdersError(e);
            }

            @Override public void onNext(final List<Order> orders) {
                showOrders(orders);
            }

            @Override public void onCompleted() {
            }
        };
    }

    private void startLoadingOrders() {
        if (actionModeHelper.isActive()) {
            actionModeHelper.reset();
        }
        swipeRefreshLayout.setRefreshing(true);
        fastItemAdapter.clear();
        recyclerViewOrders.setAdapter(null);
        recyclerViewOrders.setVisibility(View.GONE);
        mLinearLayoutEmptyState.setVisibility(View.VISIBLE);
        getActivity().invalidateOptionsMenu();
    }

    private void handleLoadOrdersError(Throwable e) {
        Timber.e(e, "Could not load orders");
        swipeRefreshLayout.setRefreshing(false);
        mLinearLayoutErrorState.setVisibility(View.VISIBLE);
        mLinearLayoutEmptyState.setVisibility(View.GONE);
    }

    private void showOrders(List<Order> orders) {
        if (!orders.isEmpty()) {
            recyclerViewOrders.setVisibility(View.VISIBLE);

            List<OrderAdapterItem> itens = new ArrayList<>();
            for (Order order : orders) {
                itens.add(OrderAdapterItem
                        .create(!isShowOnlyPendingOrders())
                        .withOrder(order)
                        .withIdentifier(order.getId()));
            }
            fastItemAdapter.add(itens);
            recyclerViewOrders.setAdapter(fastItemAdapter);

            recyclerViewOrders
                    .getViewTreeObserver()
                    .addOnGlobalLayoutListener(
                            recyclerViewLayoutListener = this::onRecyclerViewFinishLoading);
        } else {
            swipeRefreshLayout.setRefreshing(false);
            if (!eventBus().isRegistered(this)) {
                eventBus().register(this);
            }
        }
    }

    private void onRecyclerViewFinishLoading() {
        if (getView() != null) {
            recyclerViewOrders
                    .getViewTreeObserver()
                    .removeOnGlobalLayoutListener(recyclerViewLayoutListener);
            recyclerViewLayoutListener = null;
            getActivity().invalidateOptionsMenu();
            swipeRefreshLayout.setRefreshing(false);
            mLinearLayoutEmptyState.setVisibility(View.GONE);
            if (!eventBus().isRegistered(this)) {
                eventBus().register(this);
            }
        }
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        outState = fastItemAdapter.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override public void onDestroyView() {
        if (currentSubscription != null && !currentSubscription.isUnsubscribed()) {
            currentSubscription.unsubscribe();
        }
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
            mLinearLayoutEmptyState.setVisibility(View.GONE);
        }
        eventBus().unregister(this);
        super.onDestroyView();
    }

    private class ActionBarCallback implements ActionMode.Callback {

        @Override public boolean onCreateActionMode(final ActionMode mode, final Menu menu) {
            return true;
        }

        @Override public boolean onPrepareActionMode(final ActionMode mode, final Menu menu) {
            final int selectionSize = fastItemAdapter.getSelectedItems().size();
            final MenuItem duplicateMenuItem = menu.findItem(R.id.action_duplicate_order);
            if (selectionSize == 1) {
                if (!duplicateMenuItem.isVisible()) {
                    duplicateMenuItem.setVisible(true);
                }

                OrderAdapterItem[] orderAdapterItems =
                        fastItemAdapter.getSelectedItems()
                                .toArray(new OrderAdapterItem[selectionSize]);
                Order firstOrder = orderAdapterItems[0].getOrder();
                if (firstOrder.isStatusSyncedOrCancelled()) {
                    MenuItem syncItemsMenuItem = menu.findItem(R.id.action_sync);
                    if (syncItemsMenuItem.isVisible()) {
                        syncItemsMenuItem.setVisible(false);
                    }
                }

                return true;
            } else if (selectionSize > 1 && duplicateMenuItem.isVisible()) {
                duplicateMenuItem.setVisible(false);
                return true;
            }

            return false;
        }

        @Override public boolean onActionItemClicked(final ActionMode mode, final MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_sync: {
                    Set<OrderAdapterItem> selectedItems = fastItemAdapter.getSelectedItems();
                    List<Order> orders = new ArrayList<>();
                    for (OrderAdapterItem orderAdapterItem : selectedItems) {
                        orders.add(orderAdapterItem.getOrder());
                    }
                    eventBus().postSticky(SyncOrdersEvent.just(orders));
                    SyncTaskService.scheduleSingleSync(getContext());
                    EventTracker.action(ACTION_MANUAL_SYNC);
                    Snackbar.make(getView(), R.string.order_list_will_update_after_sync,
                            Snackbar.LENGTH_SHORT)
                            .show();
                    break;
                }
                case R.id.action_duplicate_order: {
                    Set<OrderAdapterItem> selectedItems = fastItemAdapter.getSelectedItems();
                    if (selectedItems != null && selectedItems.size() == 1) {
                        OrderAdapterItem orderAdapterItem = selectedItems.iterator().next();
                        eventBus().postSticky(duplicateOrder(orderAdapterItem.getOrder()));
                        EventTracker.action(ACTION_DUPLICATE_ORDER);
                        navigate().toAddOrder();
                    }
                    break;
                }
            }

            mode.finish();
            return true;
        }

        @Override public void onDestroyActionMode(final ActionMode mode) {}
    }
}
