package br.com.libertsolutions.libertvendas.app.presentation.dashboard;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
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
import br.com.libertsolutions.libertvendas.app.domain.pojo.LoggedUser;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Order;
import br.com.libertsolutions.libertvendas.app.domain.pojo.OrderChartData;
import br.com.libertsolutions.libertvendas.app.presentation.addorder.orderform.SavedOrderEvent;
import br.com.libertsolutions.libertvendas.app.presentation.base.BaseFragment;
import br.com.libertsolutions.libertvendas.app.presentation.main.LoggedInUserEvent;
import br.com.libertsolutions.libertvendas.app.presentation.util.EventTracker;
import butterknife.BindView;
import butterknife.OnClick;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.joda.time.DateTime;
import rx.Subscriber;
import rx.Subscription;
import timber.log.Timber;

import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.providerOrderRepository;
import static br.com.libertsolutions.libertvendas.app.data.order.OrderStatusSpecificationFilter.NOT_CANCELLED;
import static br.com.libertsolutions.libertvendas.app.presentation.util.DateUtils.BEFORE_MIDNIGHT;
import static br.com.libertsolutions.libertvendas.app.presentation.util.DateUtils.convertFromZeroBasedIndex;
import static br.com.libertsolutions.libertvendas.app.presentation.util.DateUtils.dateTimeToMillis;
import static br.com.libertsolutions.libertvendas.app.presentation.util.DateUtils.getDay;
import static br.com.libertsolutions.libertvendas.app.presentation.util.DateUtils.getMonth;
import static br.com.libertsolutions.libertvendas.app.presentation.util.DateUtils.getYear;
import static br.com.libertsolutions.libertvendas.app.presentation.util.DateUtils.toDateTime;
import static br.com.libertsolutions.libertvendas.app.presentation.util.EventTracker.ACTION_FILTERED_GRAPH;
import static org.joda.time.LocalTime.MIDNIGHT;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * @author Filipe Bezerra
 */
public class DashboardFragment extends BaseFragment
        implements DatePickerDialog.OnDateSetListener {

    public static final String TAG = DashboardFragment.class.getName();

    private OrderRepository orderRepository;

    private Subscription currentSubscription;

    private LoggedUser loggedUser;

    private OnGlobalLayoutListener pieChartLayoutListener = null;

    private DateTime initialDateFilter;

    private DateTime finalDateFilter;

    @BindView(R.id.swipe_container_all_pull_refresh) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.pie_chart_orders_by_customer) PieChart pieChart;

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override protected int provideContentViewResource() {
        return R.layout.fragment_dashboard;
    }

    @Override public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable @Override public View onCreateView(final LayoutInflater inflater,
            @Nullable final ViewGroup container, @Nullable final Bundle inState) {
        View view = super.onCreateView(inflater, container, inState);

        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);

        drawPieChart();

        orderRepository = providerOrderRepository();
        eventBus().register(this);

        return view;
    }

    @Override public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.menu_filter, menu);
    }

    @Override public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.action_all_filter) {
            showFilterDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override public void onDateSet(final DatePickerDialog view,
            final int year, final int monthOfYear, final int dayOfMonth,
            final int yearEnd, final int monthOfYearEnd, final int dayOfMonthEnd) {
        initialDateFilter = toDateTime(
                year, convertFromZeroBasedIndex(monthOfYear), dayOfMonth, MIDNIGHT);
        finalDateFilter = toDateTime(
                yearEnd, convertFromZeroBasedIndex(monthOfYearEnd), dayOfMonthEnd, BEFORE_MIDNIGHT);
        EventTracker.action(ACTION_FILTERED_GRAPH);
        loadOrderedOrders();
    }

    @Subscribe(sticky = true) public void onLoggedInUserEvent(LoggedInUserEvent event) {
        if (loggedUser == null || !loggedUser.equals(event.getUser())) {
            loggedUser = event.getUser();
            loadOrderedOrders();
        }
    }

    @Subscribe(sticky = true) public void onSavedOrder(SavedOrderEvent event) {
        loadOrderedOrders();
    }

    @Subscribe public void onOrdersSynced(OrdersSyncedEvent event) {
        getActivity().runOnUiThread(this::loadOrderedOrders);
    }

    @OnClick(R.id.button_all_retry) void onButtonRetryClicked() {
        mLinearLayoutErrorState.setVisibility(View.GONE);
        loadOrderedOrders();
    }

    private void drawPieChart() {
        pieChart.getDescription().setEnabled(false);

        //Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");

        //pieChart.setCenterTextTypeface(tf);
        pieChart.setCenterText(generateCenterText());
        pieChart.setCenterTextSize(10f);
        //pieChart.setCenterTextTypeface(tf);

        // radius of the center hole in percent of maximum radius
        pieChart.setHoleRadius(45f);
        pieChart.setTransparentCircleRadius(50f);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
    }

    private SpannableString generateCenterText() {
        SpannableString s = new SpannableString(getString(R.string.dashboard_chart_center_text));
        s.setSpan(new RelativeSizeSpan(2f), 0, 7, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 8, s.length(), 0);
        return s;
    }

    private void loadOrderedOrders() {
        if (getLoggedUser() != null) {
            if (initialDateFilter == null || finalDateFilter == null) {
                final DateTime now = DateTime.now();
                final int year = now.getYear();
                final int monthOfYear = now.getMonthOfYear();
                final int dayOfMonth = now.getDayOfMonth();
                initialDateFilter = toDateTime(year, monthOfYear, dayOfMonth, MIDNIGHT);
                finalDateFilter = toDateTime(year, monthOfYear, dayOfMonth, BEFORE_MIDNIGHT);
            }

            long initialDate = dateTimeToMillis(initialDateFilter);
            long finalDate = dateTimeToMillis(finalDateFilter);

            currentSubscription = orderRepository
                    .query(new OrdersByUserSpecification(getSalesmanId(), getCompanyId())
                            .byStatus(NOT_CANCELLED)
                            .byIssueDate(initialDate, finalDate)
                            .orderByCustomerName())
                    .map(this::toChartData)
                    .observeOn(mainThread())
                    .doOnUnsubscribe(() -> swipeRefreshLayout.setRefreshing(false))
                    .subscribe(createOrderChartDataListSubscriber());
        }
    }

    private LoggedUser getLoggedUser() {
        if (loggedUser == null) {
            LoggedInUserEvent event = eventBus().getStickyEvent(LoggedInUserEvent.class);
            if (event != null) {
                loggedUser = event.getUser();
            }
        }
        return loggedUser;
    }

    private int getSalesmanId() {
        return getLoggedUser().getSalesman().getSalesmanId();
    }

    private int getCompanyId() {
        return getLoggedUser().getDefaultCompany().getCompanyId();
    }

    private List<OrderChartData> toChartData(List<Order> orders) {
        if (orders.isEmpty()) {
            return Collections.emptyList();
        }
        List<OrderChartData> chartData = new ArrayList<>();
        float orderAmount = 0;
        String name = orders.get(0).getCustomer().getName();
        for (Order order : orders) {
            if (!name.equals(order.getCustomer().getName())) {
                chartData.add(OrderChartData.create(name, orderAmount));
                name = order.getCustomer().getName();
                orderAmount = 0;
            }

            orderAmount += order.getTotalOrder();

            if (orders.indexOf(order) == orders.size() - 1) {
                chartData.add(OrderChartData.create(name, orderAmount));
            }
        }
        return chartData;
    }

    private Subscriber<List<OrderChartData>> createOrderChartDataListSubscriber() {
        return new Subscriber<List<OrderChartData>>() {
            @Override public void onStart() {
                startLoadingOrderedOrders();
            }

            @Override public void onError(final Throwable e) {
                handleLoadOrderedOrdersError(e);
            }

            @Override public void onNext(final List<OrderChartData> orders) {
                showOrderedOrders(orders);
            }

            @Override public void onCompleted() {
            }
        };
    }

    private void startLoadingOrderedOrders() {
        clearPieChart(false);
        swipeRefreshLayout.setRefreshing(true);
        pieChart.setVisibility(View.GONE);
        mLinearLayoutEmptyState.setVisibility(View.VISIBLE);
    }

    private void clearPieChart(final boolean setAsNull) {
        if (pieChart != null) {
            pieChart.clearAnimation();
            if (!pieChart.isEmpty()) {
                pieChart.clearValues();
            }
            pieChart.clear();
            if (setAsNull) {
                pieChart = null;
            }
        }
    }

    private void handleLoadOrderedOrdersError(Throwable e) {
        Timber.e(e, "Could not chart data");
        swipeRefreshLayout.setRefreshing(false);
        mLinearLayoutErrorState.setVisibility(View.VISIBLE);
        mLinearLayoutEmptyState.setVisibility(View.GONE);
    }

    private void showOrderedOrders(List<OrderChartData> orders) {
        if (!orders.isEmpty()) {
            pieChart.setVisibility(View.VISIBLE);
            pieChart.getViewTreeObserver()
                    .addOnGlobalLayoutListener(
                            pieChartLayoutListener = this::onPieChartFinishLoading);
            convertToPieData(orders);
        } else {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void convertToPieData(List<OrderChartData> data) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        for (OrderChartData item : data) {
            entries.add(new PieEntry(item.getAmount(), item.getName()));
        }

        PieDataSet ds1 = new PieDataSet(entries, getString(R.string.dashboard_chart_label));
        ds1.setColors(ColorTemplate.MATERIAL_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.WHITE);
        ds1.setValueTextSize(12f);

        //Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");

        PieData pieData = new PieData(ds1);
        //pieData.setValueTypeface(tf);
        pieData.setValueFormatter(new DefaultValueFormatter(2));

        showChartData(pieData);
    }

    private void showChartData(PieData pieData) {
        pieChart.setData(pieData);
        pieChart.setDrawEntryLabels(false);
        pieChart.invalidate();
    }

    private void onPieChartFinishLoading() {
        if (getView() != null) {
            pieChart
                    .getViewTreeObserver()
                    .removeOnGlobalLayoutListener(pieChartLayoutListener);
            pieChartLayoutListener = null;
            swipeRefreshLayout.setRefreshing(false);
            mLinearLayoutEmptyState.setVisibility(View.GONE);
        }
    }

    private void showFilterDialog() {
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this,
                getYear(initialDateFilter), getMonth(initialDateFilter), getDay(initialDateFilter),
                getYear(finalDateFilter), getMonth(finalDateFilter), getDay(finalDateFilter));

        datePickerDialog.setStartTitle(getString(R.string.all_issue_date_initial));
        datePickerDialog.setEndTitle(getString(R.string.all_issue_date_final));
        datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
    }

    @Override public void onDestroyView() {
        if (currentSubscription != null && !currentSubscription.isUnsubscribed()) {
            currentSubscription.unsubscribe();
        }
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
            mLinearLayoutEmptyState.setVisibility(View.GONE);
        }
        clearPieChart(true);
        eventBus().unregister(this);
        super.onDestroyView();
    }
}
