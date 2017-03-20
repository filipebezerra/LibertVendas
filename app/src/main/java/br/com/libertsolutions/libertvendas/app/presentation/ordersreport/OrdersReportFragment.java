package br.com.libertsolutions.libertvendas.app.presentation.ordersreport;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.base.BaseFragment;

/**
 * @author Filipe Bezerra
 */
public class OrdersReportFragment extends BaseFragment {

    public static final String TAG = OrdersReportFragment.class.getName();

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

    @Override public void onViewCreated(
            final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showEmpty();
    }

    private void showEmpty() {
        mLinearLayoutEmptyState.setVisibility(View.VISIBLE);
    }
}
