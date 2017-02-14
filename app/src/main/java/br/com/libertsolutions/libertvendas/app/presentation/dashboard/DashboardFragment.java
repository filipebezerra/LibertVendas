package br.com.libertsolutions.libertvendas.app.presentation.dashboard;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ChartGroupedData;
import br.com.libertsolutions.libertvendas.app.presentation.fragment.LibertVendasFragment;
import butterknife.BindView;
import butterknife.OnClick;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.List;

import static br.com.libertsolutions.libertvendas.app.DataInjection.LocalRepositories.providePedidoRepository;

/**
 * @author Filipe Bezerra
 */
public class DashboardFragment extends LibertVendasFragment implements DashboardContract.View {

    public static final String TAG = DashboardFragment.class.getName();

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @BindView(R.id.pie_chart_orders_by_customer) protected PieChart mPieChart;
    @BindView(R.id.progress_bar_all_loading) protected ProgressBar mProgressBar;
    @BindView(R.id.linear_layout_all_error_state) protected LinearLayout mLinearLayoutErrorState;
    @BindView(R.id.linear_layout_all_empty_state) protected LinearLayout mLinearLayoutEmptyState;

    private DashboardContract.Presenter mPresenter;

    @Override protected int provideContentViewResource() {
        return R.layout.fragment_dashboard;
    }

    @Override public void onViewCreated(
            final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        drawPieChart();

        mPresenter = new DashboardPresenter(providePedidoRepository());
        mPresenter.attachView(this);
        mPresenter.registerEventBus();
    }

    private void drawPieChart() {
        mPieChart.getDescription().setEnabled(false);

        //Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");

        //mPieChart.setCenterTextTypeface(tf);
        mPieChart.setCenterText(generateCenterText());
        mPieChart.setCenterTextSize(10f);
        //mPieChart.setCenterTextTypeface(tf);

        // radius of the center hole in percent of maximum radius
        mPieChart.setHoleRadius(45f);
        mPieChart.setTransparentCircleRadius(50f);

        Legend l = mPieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
    }

    @Override public void onStart() {
        super.onStart();
        mPresenter.loadOrdersGrouped();
    }

    @Override public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override public void showError() {
        mLinearLayoutErrorState.setVisibility(View.VISIBLE);
    }

    @Override public void hideError() {
        mLinearLayoutErrorState.setVisibility(View.GONE);
    }

    @OnClick(R.id.button_all_retry) void onButtonRetryClicked() {
        mPresenter.retryLoadOrdersGrouped();
    }

    @Override public void displayOrdersGrouped(final List<ChartGroupedData> ordersGrouped) {
        mPieChart.setVisibility(View.VISIBLE);
        generatePieData(ordersGrouped);
    }

    private void generatePieData(List<ChartGroupedData> data) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        for(ChartGroupedData item : data) {
            entries.add(new PieEntry(item.getAmount(), item.getName()));
        }

        PieDataSet ds1 = new PieDataSet(entries, "Todos Pedidos realizados");
        ds1.setColors(ColorTemplate.MATERIAL_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.WHITE);
        ds1.setValueTextSize(12f);

        //Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");

        PieData pieData = new PieData(ds1);
        //pieData.setValueTypeface(tf);
        pieData.setValueFormatter(new DefaultValueFormatter(2));

        mPieChart.setData(pieData);
        mPieChart.setDrawEntryLabels(false);
        mPieChart.invalidate();
    }

    @Override public void showEmpty() {
        mLinearLayoutEmptyState.setVisibility(View.VISIBLE);
    }

    @Override public void hideEmpty() {
        mLinearLayoutEmptyState.setVisibility(View.GONE);
    }

    @Override public void hideChart() {
        mPieChart.setVisibility(View.GONE);
    }

    private SpannableString generateCenterText() {
        SpannableString s = new SpannableString("Pedidos\nPor Cliente");
        s.setSpan(new RelativeSizeSpan(2f), 0, 7, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 8, s.length(), 0);
        return s;
    }

    @Override public void onDestroyView() {
        mPresenter.detach();
        super.onDestroyView();
    }
}
