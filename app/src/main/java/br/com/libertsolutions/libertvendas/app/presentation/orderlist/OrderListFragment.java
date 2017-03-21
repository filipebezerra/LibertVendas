package br.com.libertsolutions.libertvendas.app.presentation.orderlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.ContextMenu;
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
import br.com.libertsolutions.libertvendas.app.domain.pojo.LoggedUser;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Order;
import br.com.libertsolutions.libertvendas.app.domain.pojo.OrderStatus;
import br.com.libertsolutions.libertvendas.app.presentation.addorder.orderform.SavedOrderEvent;
import br.com.libertsolutions.libertvendas.app.presentation.base.BaseFragment;
import br.com.libertsolutions.libertvendas.app.presentation.main.LoggedInUserEvent;
import br.com.libertsolutions.libertvendas.app.presentation.widget.recyclerview.OnItemClickListener;
import br.com.libertsolutions.libertvendas.app.presentation.widget.recyclerview.OnItemTouchListener;
import butterknife.BindView;
import butterknife.OnClick;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import rx.Subscriber;
import rx.Subscription;
import timber.log.Timber;

import static android.support.v7.widget.RecyclerView.NO_POSITION;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.providerOrderRepository;
import static br.com.libertsolutions.libertvendas.app.presentation.orderlist.SelectedOrderEvent.duplicateOrder;
import static br.com.libertsolutions.libertvendas.app.presentation.orderlist.SelectedOrderEvent.selectOrder;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * @author Filipe Bezerra
 */
public class OrderListFragment extends BaseFragment
        implements OnRefreshListener, OnItemClickListener {

    private static final String ARG_SHOW_ONLY_PENDING_ORDERS
            = OrderListFragment.class.getName() + ".argShowOnlyPendingOrders";

    private SearchView mSearchView;

    private OrderRepository mOrderRepository;

    private Subscription mCurrentSubscription;

    private OrderListAdapter mOrderListAdapter;

    protected OnGlobalLayoutListener mRecyclerViewLayoutListener = null;

    protected OnItemTouchListener mRecyclerViewItemTouchListener = null;

    protected LoggedUser mLoggedUser;

    @BindView(R.id.swipe_container_all_pull_refresh) protected SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view_orders) protected RecyclerView mRecyclerViewOrders;

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

        mRecyclerViewOrders.setHasFixedSize(true);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);

        mOrderRepository = providerOrderRepository();

        return view;
    }

    @Override public void onViewCreated(
            final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerForContextMenu(mRecyclerViewOrders);
        loadOrders();
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_all_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setQueryHint(getString(R.string.order_list_search_hint));
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override public boolean onQueryTextChange(String newText) {
                if (mOrderListAdapter != null) {
                    mOrderListAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
    }

    @Override public void onPrepareOptionsMenu(final Menu menu) {
        menu.findItem(R.id.action_all_search)
                .setVisible(mOrderListAdapter != null && !mOrderListAdapter.isEmptyList());
    }

    @Override public void onRefresh() {
        loadOrders();
    }

    @Override public void onSingleTapUp(final View view, final int position) {
        Order selectedOrder = mOrderListAdapter.getOrder(position);
        if (selectedOrder != null) {
            eventBus().postSticky(selectOrder(selectedOrder));

            if (selectedOrder.getStatus() == OrderStatus.STATUS_SYNCED ||
                    selectedOrder.getStatus() == OrderStatus.STATUS_CANCELLED) {
                navigate().toViewOrder();
            } else {
                navigate().toAddOrder();
            }
        }
    }

    @Override public void onCreateContextMenu(
            final ContextMenu menu, final View v, final ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.menu_order_list, menu);
    }

    @Override public boolean onContextItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.action_order_list_duplicate_item) {
            Object tag = mRecyclerViewOrders.getTag();
            if (tag != null) {
                Order order = (Order)tag;
                eventBus().postSticky(duplicateOrder(order));
                navigate().toAddOrder();
            }
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    @Override public void onLongPress(final View view, final int position) {
        mRecyclerViewOrders.setTag(mOrderListAdapter.getOrder(position));
        getActivity().openContextMenu(mRecyclerViewOrders);
    }

    @OnClick(R.id.button_all_retry) void onButtonRetryClicked() {
        mLinearLayoutErrorState.setVisibility(View.GONE);
        loadOrders();
    }

    @Subscribe(sticky = true) public void onLoggedInUserEvent(LoggedInUserEvent event) {
        if (mLoggedUser != null && !mLoggedUser.equals(event.getUser())) {
            mLoggedUser = event.getUser();
            loadOrders();
        }
    }

    @Subscribe(sticky = true) public void onSavedOrder(SavedOrderEvent event) {
        final Order order = event.getOrder();
        eventBus().removeStickyEvent(SavedOrderEvent.class);
        if (mOrderListAdapter != null) {
            final int position = mOrderListAdapter.updateOrder(order);
            if (position != NO_POSITION) {
                mRecyclerViewOrders.scrollToPosition(position);
            }
        } else {
            showOrders(new ArrayList<>(Collections.singletonList(order)));
        }
    }

    private void loadOrders() {
        OrdersByUserSpecification specification
                = new OrdersByUserSpecification(getSalesmanId(), getCompanyId())
                .orderByIssueDate();

        if (isShowOnlyPendingOrders()) {
            specification.byStatusCreatedOrModified();
        }

        mCurrentSubscription = mOrderRepository
                .query(specification)
                .observeOn(mainThread())
                .doOnUnsubscribe(() -> mSwipeRefreshLayout.setRefreshing(false))
                .subscribe(createOrderListSubscriber());
    }

    private int getSalesmanId() {
        return getLoggedUser().getSalesman().getSalesmanId();
    }

    private int getCompanyId() {
        return getLoggedUser().getDefaultCompany().getCompanyId();
    }

    private LoggedUser getLoggedUser() {
        if (mLoggedUser == null) {
            mLoggedUser = eventBus().getStickyEvent(LoggedInUserEvent.class).getUser();
        }
        return mLoggedUser;
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

            @Override public void onCompleted() {}
        };
    }

    private void startLoadingOrders() {
        mSwipeRefreshLayout.setRefreshing(true);
        mOrderListAdapter = null;
        mRecyclerViewOrders.setAdapter(null);
        mRecyclerViewOrders.setVisibility(View.GONE);
        mLinearLayoutEmptyState.setVisibility(View.VISIBLE);
        getActivity().invalidateOptionsMenu();
    }

    private void handleLoadOrdersError(Throwable e) {
        Timber.e(e, "Could not load orders");
        mSwipeRefreshLayout.setRefreshing(false);
        mLinearLayoutErrorState.setVisibility(View.VISIBLE);
        mLinearLayoutEmptyState.setVisibility(View.GONE);
    }

    private void showOrders(List<Order> orders) {
        if (!orders.isEmpty()) {
            mRecyclerViewOrders.setVisibility(View.VISIBLE);
            mRecyclerViewOrders.setAdapter(
                    mOrderListAdapter = new OrderListAdapter(orders, !isShowOnlyPendingOrders()));
            mRecyclerViewOrders
                    .getViewTreeObserver()
                    .addOnGlobalLayoutListener(
                            mRecyclerViewLayoutListener = this::onRecyclerViewFinishLoading);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            if (!eventBus().isRegistered(this)) {
                eventBus().register(this);
            }
        }
    }

    private void onRecyclerViewFinishLoading() {
        if (getView() != null) {
            mRecyclerViewOrders
                    .getViewTreeObserver()
                    .removeOnGlobalLayoutListener(mRecyclerViewLayoutListener);
            mRecyclerViewLayoutListener = null;

            if (mRecyclerViewItemTouchListener != null) {
                mRecyclerViewOrders.removeOnItemTouchListener(mRecyclerViewItemTouchListener);
                mRecyclerViewItemTouchListener = null;
            }

            mRecyclerViewOrders.addOnItemTouchListener(
                    mRecyclerViewItemTouchListener
                            = new OnItemTouchListener(getContext(), mRecyclerViewOrders, this));

            getActivity().invalidateOptionsMenu();
            mSwipeRefreshLayout.setRefreshing(false);
            mLinearLayoutEmptyState.setVisibility(View.GONE);
            if (!eventBus().isRegistered(this)) {
                eventBus().register(this);
            }
        }
    }

    @Override public void onDestroyView() {
        if (mCurrentSubscription != null && !mCurrentSubscription.isUnsubscribed()) {
            mCurrentSubscription.unsubscribe();
        }
        eventBus().unregister(this);
        super.onDestroyView();
    }
}
