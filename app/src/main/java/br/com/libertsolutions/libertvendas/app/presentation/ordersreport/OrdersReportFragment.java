package br.com.libertsolutions.libertvendas.app.presentation.ordersreport;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
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
import br.com.libertsolutions.libertvendas.app.presentation.addorder.orderform.SavedOrderEvent;
import br.com.libertsolutions.libertvendas.app.presentation.base.BaseFragment;
import br.com.libertsolutions.libertvendas.app.presentation.main.LoggedInUserEvent;
import butterknife.BindView;
import butterknife.OnClick;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import rx.Subscriber;
import rx.Subscription;
import timber.log.Timber;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;
import static android.support.v7.widget.RecyclerView.NO_POSITION;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.providerOrderRepository;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * @author Filipe Bezerra
 */
public class OrdersReportFragment extends BaseFragment implements OnRefreshListener {

    public static final String TAG = OrdersReportFragment.class.getName();

    private OrderRepository mOrderRepository;

    private Subscription mCurrentSubscription;

    private OrdersReportAdapter mOrdersReportAdapter;

    protected OnGlobalLayoutListener mRecyclerViewLayoutListener = null;

    protected LoggedUser mLoggedUser;

    @BindView(R.id.swipe_container_all_pull_refresh) protected SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view_orders_report) protected RecyclerView mRecyclerViewOrders;

    public static OrdersReportFragment newInstance() {
        return new OrdersReportFragment();
    }

    @Override protected int provideContentViewResource() {
        return R.layout.fragment_orders_report;
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
        loadOrders();
    }

    @Override public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.menu_filter, menu);
    }

    @Override public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.action_all_filter) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override public void onRefresh() {
        loadOrders();
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
        if (mOrdersReportAdapter != null) {
            final int position = mOrdersReportAdapter.updateOrder(order);
            if (position != NO_POSITION) {
                mRecyclerViewOrders.scrollToPosition(position);
            }
        } else {
            showOrders(new ArrayList<>(Collections.singletonList(order)));
        }
    }

    private void loadOrders() {
        mCurrentSubscription = mOrderRepository
                .query(new OrdersByUserSpecification(getSalesmanId(), getCompanyId())
                        .orderByIssueDate())
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
        mOrdersReportAdapter = null;
        mRecyclerViewOrders.setAdapter(null);
        mRecyclerViewOrders.setVisibility(View.GONE);
        mLinearLayoutEmptyState.setVisibility(View.VISIBLE);
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
                    mOrdersReportAdapter = new OrdersReportAdapter(orders));
            mRecyclerViewOrders.addItemDecoration(new DividerItemDecoration(getContext(), VERTICAL));
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
