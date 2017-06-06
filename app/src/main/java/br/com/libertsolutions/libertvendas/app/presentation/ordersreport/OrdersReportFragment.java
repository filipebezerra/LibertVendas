package br.com.libertsolutions.libertvendas.app.presentation.ordersreport;

import android.os.Bundle;
import android.support.annotation.IntDef;
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
import android.widget.EditText;
import android.widget.RadioGroup;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.data.order.OrderRepository;
import br.com.libertsolutions.libertvendas.app.data.order.OrdersByUserSpecification;
import br.com.libertsolutions.libertvendas.app.data.sync.OrdersSyncedEvent;
import br.com.libertsolutions.libertvendas.app.domain.pojo.LoggedUser;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Order;
import br.com.libertsolutions.libertvendas.app.presentation.addorder.orderform.SavedOrderEvent;
import br.com.libertsolutions.libertvendas.app.presentation.base.BaseFragment;
import br.com.libertsolutions.libertvendas.app.presentation.main.LoggedInUserEvent;
import br.com.libertsolutions.libertvendas.app.presentation.util.EventTracker;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.afollestad.materialdialogs.MaterialDialog;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.borax12.materialdaterangepicker.date.DatePickerDialog.OnDateSetListener;
import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import rx.Subscriber;
import rx.Subscription;
import timber.log.Timber;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;
import static android.support.v7.widget.RecyclerView.NO_POSITION;
import static android.view.MotionEvent.ACTION_UP;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.providerOrderRepository;
import static br.com.libertsolutions.libertvendas.app.data.order.OrderStatusSpecificationFilter.CREATED_OR_MODIFIED;
import static br.com.libertsolutions.libertvendas.app.data.order.OrderStatusSpecificationFilter.INVOICED;
import static br.com.libertsolutions.libertvendas.app.data.order.OrderStatusSpecificationFilter.SYNCED;
import static br.com.libertsolutions.libertvendas.app.presentation.util.DateUtils.BEFORE_MIDNIGHT;
import static br.com.libertsolutions.libertvendas.app.presentation.util.DateUtils.convertFromZeroBasedIndex;
import static br.com.libertsolutions.libertvendas.app.presentation.util.DateUtils.dateTimeToMillis;
import static br.com.libertsolutions.libertvendas.app.presentation.util.DateUtils.getDay;
import static br.com.libertsolutions.libertvendas.app.presentation.util.DateUtils.getMonth;
import static br.com.libertsolutions.libertvendas.app.presentation.util.DateUtils.getYear;
import static br.com.libertsolutions.libertvendas.app.presentation.util.DateUtils.toDateTime;
import static br.com.libertsolutions.libertvendas.app.presentation.util.EventTracker.ACTION_FILTERED_ORDERS;
import static br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils.formatAsCurrency;
import static br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils.formatAsDate;
import static butterknife.ButterKnife.findById;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import static org.joda.time.LocalTime.MIDNIGHT;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * @author Filipe Bezerra
 */
public class OrdersReportFragment extends BaseFragment
        implements OnRefreshListener, OnDateSetListener {

    public static final String TAG = OrdersReportFragment.class.getName();

    private OrderRepository orderRepository;

    private Subscription currentSubscription;

    private OrdersReportAdapter ordersReportAdapter;

    private OnGlobalLayoutListener recyclerViewLayoutListener = null;

    private LoggedUser loggedUser;

    private MaterialDialog filtersDialog;

    private DateTime initialDateFilter;

    private DateTime finalDateFilter;

    @OrdersReportFragment.StatusFilter private int statusFilter = STATUS_FILTER_ALL;

    @Retention(SOURCE)
    @IntDef({STATUS_FILTER_ALL, STATUS_FILTER_PENDING, STATUS_FILTER_SENT, STATUS_FILTER_INVOICED})
    private @interface StatusFilter {}
    private static final int STATUS_FILTER_ALL = 0;
    private static final int STATUS_FILTER_PENDING = 1;
    private static final int STATUS_FILTER_SENT = 2;
    private static final int STATUS_FILTER_INVOICED = 3;

    @BindView(R.id.swipe_container_all_pull_refresh) protected SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view_orders_report) protected RecyclerView recyclerViewOrders;

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

        recyclerViewOrders.setHasFixedSize(true);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);

        orderRepository = providerOrderRepository();

        return view;
    }

    @Override public void onViewCreated(
            final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadOrdersByDefault();
    }

    @Override public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.menu_filter, menu);
    }

    @Override public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.action_all_filter) {
            showFiltersDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override public void onRefresh() {
        loadOrdersByDefault();
    }

    @OnClick(R.id.button_all_retry) void onButtonRetryClicked() {
        mLinearLayoutErrorState.setVisibility(View.GONE);
        loadOrdersByDefault();
    }

    @Subscribe(sticky = true) public void onLoggedInUserEvent(LoggedInUserEvent event) {
        if (loggedUser != null && !loggedUser.equals(event.getUser())) {
            loggedUser = event.getUser();
            loadOrdersByDefault();
        }
    }

    @Subscribe(sticky = true) public void onSavedOrder(SavedOrderEvent event) {
        final Order order = event.getOrder();
        eventBus().removeStickyEvent(SavedOrderEvent.class);
        if (ordersReportAdapter != null) {
            final int position = ordersReportAdapter.updateOrder(order);
            if (position != NO_POSITION) {
                recyclerViewOrders.scrollToPosition(position);
            }
        } else {
            showOrders(new ArrayList<>(Collections.singletonList(order)));
        }
    }

    @Subscribe public void onOrdersSynced(OrdersSyncedEvent event) {
        getActivity().runOnUiThread(this::loadOrdersByDefault);
    }

    @Override public void onDateSet(final DatePickerDialog view,
            final int year, final int monthOfYear, final int dayOfMonth,
            final int yearEnd, final int monthOfYearEnd, final int dayOfMonthEnd) {
        setInitialIssueDateFilter(year, convertFromZeroBasedIndex(monthOfYear), dayOfMonth);
        setFinalIssueDateFilter(yearEnd, convertFromZeroBasedIndex(monthOfYearEnd), dayOfMonthEnd);
    }

    private void loadOrdersByDefault() {
        loadOrders(new OrdersByUserSpecification(getSalesmanId(), getCompanyId())
                .orderByIssueDate());
    }

    private void loadOrders(OrdersByUserSpecification specification) {
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
        swipeRefreshLayout.setRefreshing(true);
        ordersReportAdapter = null;
        recyclerViewOrders.setAdapter(null);
        recyclerViewOrders.setVisibility(View.GONE);
        mLinearLayoutEmptyState.setVisibility(View.VISIBLE);
        setTitle(getString(R.string.main_drawer_item_orders_report));
    }

    private void handleLoadOrdersError(Throwable e) {
        Timber.e(e, "Could not load orders");
        swipeRefreshLayout.setRefreshing(false);
        mLinearLayoutErrorState.setVisibility(View.VISIBLE);
        mLinearLayoutEmptyState.setVisibility(View.GONE);
    }

    private void showOrders(List<Order> orders) {
        if (!orders.isEmpty()) {
            setTitleWithTotalOrders(orders);
            recyclerViewOrders.setVisibility(View.VISIBLE);
            recyclerViewOrders.setAdapter(
                    ordersReportAdapter = new OrdersReportAdapter(orders));
            recyclerViewOrders.addItemDecoration(new DividerItemDecoration(getContext(), VERTICAL));
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

    private void setTitleWithTotalOrders(List<Order> orders) {
        double total = 0;
        for (Order order : orders) {
            total += order.getTotalOrder();
        }
        setTitle(getString(R.string.orders_report_total_title, formatAsCurrency(total)));
    }

    private void onRecyclerViewFinishLoading() {
        if (getView() != null) {
            recyclerViewOrders
                    .getViewTreeObserver()
                    .removeOnGlobalLayoutListener(recyclerViewLayoutListener);
            recyclerViewLayoutListener = null;

            swipeRefreshLayout.setRefreshing(false);
            mLinearLayoutEmptyState.setVisibility(View.GONE);
            if (!eventBus().isRegistered(this)) {
                eventBus().register(this);
            }
        }
    }

    private void showFiltersDialog() {
        filtersDialog = new MaterialDialog.Builder(getContext())
                .title(R.string.all_filters_title)
                .customView(R.layout.dialog_orders_report_filter, false)
                .positiveText(R.string.all_apply_filter)
                .onPositive((dialog, which) -> applyFilters())
                .show();

        resetFiltersInitialAndFinalDate();
        setFiltersDefaultStatus();
        setUpFiltersTouchListener();
        setUpFiltersCheckedChangeListener();
    }

    private void resetFiltersInitialAndFinalDate() {
        initialDateFilter = null;
        finalDateFilter = null;
    }

    private void setFiltersDefaultStatus() {
        final View filtersDialogView = filtersDialog.getCustomView();
        if (filtersDialogView != null) {
            RadioGroup statusRadioGroup = findById(filtersDialogView, R.id.radio_group_status);

            switch (statusFilter) {
                case STATUS_FILTER_ALL:
                    statusRadioGroup.check(R.id.radio_button_status_all);
                    break;
                case STATUS_FILTER_INVOICED:
                    statusRadioGroup.check(R.id.radio_button_status_invoiced);
                    break;
                case STATUS_FILTER_PENDING:
                    statusRadioGroup.check(R.id.radio_button_status_pending);
                    break;
                case STATUS_FILTER_SENT:
                    statusRadioGroup.check(R.id.radio_button_status_sent);
                    break;
            }
        }
    }

    private void setUpFiltersTouchListener() {
        final View filtersDialogView = filtersDialog.getCustomView();
        if (filtersDialogView != null) {
            findById(filtersDialogView, R.id.edit_text_issue_date_initial)
                    .setOnTouchListener((v, e) -> {
                        if (e.getAction() == ACTION_UP) {
                            showFilterByIssueDateDialog();
                            return false;
                        }
                        return true;
                    });

            findById(filtersDialogView, R.id.edit_text_issue_date_final)
                    .setOnTouchListener((v, e) -> {
                        if (e.getAction() == ACTION_UP) {
                            showFilterByIssueDateDialog();
                            return false;
                        }
                        return true;
                    });
        }
    }

    private void setUpFiltersCheckedChangeListener() {
        final View filtersDialogView = filtersDialog.getCustomView();
        if (filtersDialogView != null) {
            ButterKnife.<RadioGroup>findById(filtersDialogView, R.id.radio_group_status)
                    .setOnCheckedChangeListener((group, checkedId) -> {
                        switch (checkedId) {
                            case R.id.radio_button_status_all: {
                                statusFilter = STATUS_FILTER_ALL;
                                break;
                            }
                            case R.id.radio_button_status_pending: {
                                statusFilter = STATUS_FILTER_PENDING;
                                break;
                            }
                            case R.id.radio_button_status_sent: {
                                statusFilter = STATUS_FILTER_SENT;
                                break;
                            }
                            case R.id.radio_button_status_invoiced: {
                                statusFilter = STATUS_FILTER_INVOICED;
                                break;
                            }
                        }
                    });
        }
    }

    private void applyFilters() {
        OrdersByUserSpecification specification = new OrdersByUserSpecification(
                getSalesmanId(), getCompanyId());

        if (initialDateFilter != null && finalDateFilter != null) {
            long initialDate = dateTimeToMillis(initialDateFilter);
            long finalDate = dateTimeToMillis(finalDateFilter);
            specification.byIssueDate(initialDate, finalDate);
        }

        switch (statusFilter) {
            case STATUS_FILTER_PENDING: {
                specification.byStatus(CREATED_OR_MODIFIED);
                break;
            }
            case STATUS_FILTER_SENT: {
                specification.byStatus(SYNCED);
                break;
            }
            case STATUS_FILTER_INVOICED: {
                specification.byStatus(INVOICED);
                break;
            }
        }

        EventTracker.action(ACTION_FILTERED_ORDERS);
        loadOrders(specification);
    }

    private void showFilterByIssueDateDialog() {
        final LocalDate now = LocalDate.now();
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this,
                getYear(now), getMonth(now), getDay(now),
                getYear(now), getMonth(now), getDay(now));

        datePickerDialog.setStartTitle(getString(R.string.all_issue_date_initial));
        datePickerDialog.setEndTitle(getString(R.string.all_issue_date_final));
        datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
    }

    private void setInitialIssueDateFilter(final int year, final int month, final int day) {
        initialDateFilter = toDateTime(year, month, day, MIDNIGHT);
        EditText editText = findById(filtersDialog.getCustomView(), R.id.edit_text_issue_date_initial);
        editText.setText(formatAsDate(initialDateFilter));
    }

    private void setFinalIssueDateFilter(final int year, final int month, final int day) {
        finalDateFilter = toDateTime(year, month, day, BEFORE_MIDNIGHT);
        EditText editText = findById(filtersDialog.getCustomView(), R.id.edit_text_issue_date_final);
        editText.setText(formatAsDate(finalDateFilter));
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
}
