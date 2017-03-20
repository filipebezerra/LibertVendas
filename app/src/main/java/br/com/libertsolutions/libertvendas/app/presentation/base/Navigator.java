package br.com.libertsolutions.libertvendas.app.presentation.base;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.addcustomer.AddCustomerActivity;
import br.com.libertsolutions.libertvendas.app.presentation.addorder.AddOrderActivity;
import br.com.libertsolutions.libertvendas.app.presentation.citylist.CityListActivity;
import br.com.libertsolutions.libertvendas.app.presentation.customerlist.CustomerListFragment;
import br.com.libertsolutions.libertvendas.app.presentation.dashboard.DashboardFragment;
import br.com.libertsolutions.libertvendas.app.presentation.initialflow.InitialFlowActivity;
import br.com.libertsolutions.libertvendas.app.presentation.orderlist.OrderListPageFragment;
import br.com.libertsolutions.libertvendas.app.presentation.ordersreport.OrdersReportFragment;
import br.com.libertsolutions.libertvendas.app.presentation.productlist.ProductListFragment;
import br.com.libertsolutions.libertvendas.app.presentation.settings.SettingsActivity;
import br.com.libertsolutions.libertvendas.app.presentation.vieworder.ViewOrderActivity;

/**
 * @author Filipe Bezerra
 */
public class Navigator {

    public static final int REQUEST_INITIAL_FLOW = 0x1;

    public static final int REQUEST_SELECT_CITY = 0x2;

    private final BaseActivity mActivity;

    Navigator(final BaseActivity activity) {
        mActivity = activity;
    }

    public void toInitialFlow() {
        ActivityCompat.startActivityForResult(mActivity,
                new Intent(mActivity, InitialFlowActivity.class), REQUEST_INITIAL_FLOW, null);
    }

    public void toDashboard() {
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_all_fragment_container,
                        DashboardFragment.newInstance(), DashboardFragment.TAG)
                .commit();
        mActivity.setTitle(R.string.dashboard_fragment_title);
    }

    public void toOrdersReport() {
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_all_fragment_container,
                        OrdersReportFragment.newInstance(), OrdersReportFragment.TAG)
                .commit();
        mActivity.setTitle(R.string.orders_report_fragment_title);
    }

    public void toOrderList() {
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_all_fragment_container,
                        OrderListPageFragment.newInstance(), OrderListPageFragment.TAG)
                .commit();
        mActivity.setTitle(R.string.order_list_fragment_title);
    }

    public void toCustomerList() {
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_all_fragment_container,
                        CustomerListFragment.newInstance(), CustomerListFragment.TAG)
                .commit();
        mActivity.setTitle(R.string.customer_list_fragment_title);
    }

    public void toProductList() {
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_all_fragment_container,
                        ProductListFragment.newInstance(), ProductListFragment.TAG)
                .commit();
        mActivity.setTitle(R.string.product_list_fragment_title);
    }

    public void toSettings() {
        ActivityCompat
                .startActivity(mActivity, new Intent(mActivity, SettingsActivity.class), null);
    }

    public void toAddCustomer() {
        ActivityCompat
                .startActivity(mActivity, new Intent(mActivity, AddCustomerActivity.class), null);
    }

    public void toCityList() {
        ActivityCompat
                .startActivityForResult(mActivity, new Intent(mActivity, CityListActivity.class),
                        REQUEST_SELECT_CITY, null);
    }

    public void toAddOrder() {
        ActivityCompat
                .startActivity(mActivity, new Intent(mActivity, AddOrderActivity.class), null);
    }

    public void toViewOrder() {
        ActivityCompat
                .startActivity(mActivity, new Intent(mActivity, ViewOrderActivity.class), null);
    }
}
