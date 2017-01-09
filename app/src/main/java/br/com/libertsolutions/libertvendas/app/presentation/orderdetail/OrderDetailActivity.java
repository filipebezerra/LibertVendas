package br.com.libertsolutions.libertvendas.app.presentation.orderdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;
import br.com.libertsolutions.libertvendas.app.presentation.widget.TabAdapter;
import butterknife.BindView;

/**
 * @author Filipe Bezerra
 */
public class OrderDetailActivity extends LibertVendasActivity {

    static final String EXTRA_ORDER_TO_DETAIL
            = OrderDetailActivity.class.getSimpleName() + ".extraOrderToDetail";

    @BindView(R.id.fragment_container) protected ViewPager mViewPager;

    private TabAdapter mTabAdapter;

    public static void create(@NonNull LibertVendasActivity fromContext, @NonNull Pedido order) {

        final Intent intent = new Intent(fromContext, OrderDetailActivity.class)
                .putExtra(EXTRA_ORDER_TO_DETAIL, order);

        ActivityCompat.startActivity(fromContext, intent, null);
    }

    @Override protected int provideContentViewResource() {
        return R.layout.activity_order_detail;
    }

    @Override protected void onCreate(@Nullable final Bundle inState) {
        super.onCreate(inState);
        setAsInitialFlowActivity();

        final Pedido order = getIntent().getExtras().getParcelable(EXTRA_ORDER_TO_DETAIL);
        if (order == null) {
            throw new IllegalStateException(
                    "To run this activity, you must provide extra data using create static method");
        }

        mTabAdapter = new TabAdapter(getSupportFragmentManager());
        mTabAdapter.addFragment(
                OrderItemsFragment.create(), getString(R.string.title_fragment_order_items));
        mTabAdapter.addFragment(
                OrderInfoFragment.create(), getString(R.string.title_fragment_order_info));

        mViewPager.setAdapter(mTabAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(
                    int position, float positionOffset, int positionOffsetPixels) {}

            @Override public void onPageSelected(int position) {
                getSupportActionBar().setSubtitle(mTabAdapter.getPageTitle(position));
            }

            @Override public void onPageScrollStateChanged(int state) {}
        });

        getSupportActionBar().setTitle(getString(R.string.new_title_activity_order_detail,
                order.getIdPedido()));
        getSupportActionBar().setSubtitle(mTabAdapter.getPageTitle(mViewPager.getCurrentItem()));
    }
}
