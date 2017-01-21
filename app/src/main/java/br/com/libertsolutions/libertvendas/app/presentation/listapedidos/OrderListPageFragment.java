package br.com.libertsolutions.libertvendas.app.presentation.listapedidos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.fragment.LibertVendasFragment;
import br.com.libertsolutions.libertvendas.app.presentation.widget.TabAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Filipe Bezerra
 */
public class OrderListPageFragment extends LibertVendasFragment {

    public static final String TAG = OrderListPageFragment.class.getName();

    @BindView(R.id.view_pager) protected ViewPager mViewPager;

    private AppBarLayout mAppBarLayoutActivity;

    private TabLayout mTabLayout;

    private TabAdapter mTabAdapter;

    public static OrderListPageFragment newInstance() {
        return new OrderListPageFragment();
    }

    @Override protected int provideContentViewResource() {
        return R.layout.fragment_tabs;
    }

    @Override public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mAppBarLayoutActivity == null) {
            mAppBarLayoutActivity = ButterKnife.findById(getActivity(), R.id.app_bar);
        }

        if (mTabLayout == null) {
            mTabLayout = (TabLayout) LayoutInflater.from(getContext())
                    .inflate(R.layout.tab_layout, mAppBarLayoutActivity, false);

            mTabAdapter = new TabAdapter(getChildFragmentManager());
            mTabAdapter.addFragment(OrderListFragment.newInstance(false),
                    getString(R.string.title_fragment_orders_list));
            mTabAdapter.addFragment(OrderListFragment.newInstance(true),
                    getString(R.string.title_fragment_pending_orders_list));
            mViewPager.setAdapter(mTabAdapter);
            mViewPager.setOffscreenPageLimit(2);

            mTabLayout.setupWithViewPager(mViewPager);
        }

        mAppBarLayoutActivity.addView(mTabLayout);
    }

    @Override public void onActivityResult(
            final int requestCode, final int resultCode, final Intent data) {
        for (Fragment fragment : mTabAdapter.getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override public void onDestroyView() {
        mAppBarLayoutActivity.removeView(mTabLayout);
        super.onDestroyView();
    }
}
