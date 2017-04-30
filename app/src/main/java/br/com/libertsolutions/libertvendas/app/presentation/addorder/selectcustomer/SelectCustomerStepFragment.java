package br.com.libertsolutions.libertvendas.app.presentation.addorder.selectcustomer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Customer;
import br.com.libertsolutions.libertvendas.app.presentation.customerlist.CustomerListFragment;
import br.com.libertsolutions.libertvendas.app.presentation.orderlist.SelectedOrderEvent;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import static android.support.design.widget.Snackbar.LENGTH_SHORT;
import static android.text.TextUtils.isEmpty;
import static br.com.libertsolutions.libertvendas.app.R.string.select_customer_step_not_selected_error;
import static br.com.libertsolutions.libertvendas.app.presentation.customerlist.SelectedCustomerEvent.selectCustomer;

/**
 * @author Filipe Bezerra
 */
public class SelectCustomerStepFragment extends CustomerListFragment implements Step {

    private Customer mSelectedCustomer;

    public static SelectCustomerStepFragment newInstance() {
        return new SelectCustomerStepFragment();
    }

    @Override protected int provideContentViewResource() {
        return R.layout.fragment_customer_list;
    }

    @Override public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SelectedOrderEvent event = eventBus().getStickyEvent(SelectedOrderEvent.class);
        if (event != null) {
            mSelectedCustomer = event.getOrder().getCustomer();
        }
    }

    @Override protected void onSelectedCustomerFromList(@NonNull final Customer selectedCustomer) {
        mSelectedCustomer = selectedCustomer;
        eventBus().post(selectCustomer(selectedCustomer));
    }

    @Override protected void onRecyclerViewFinishLoading() {
        super.onRecyclerViewFinishLoading();
        if (getView() != null) {
            mSwipeRefreshLayout.setEnabled(false);
            showSelectedCustomer();
        }
    }

    @Override public VerificationError verifyStep() {
        if (!isEmpty(mSearchView.getQuery())) {
            mSearchView.setQuery("", false);
            mSearchView.clearFocus();
            mCustomerListAdapter.getFilter().filter("");
        }

        return mSelectedCustomer == null ?
                new VerificationError(getString(select_customer_step_not_selected_error)) :
                null;
    }

    @Override public void onSelected() {
        showSelectedCustomer();
    }

    @Override public void onError(@NonNull final VerificationError error) {
        Snackbar.make(getView(), error.getErrorMessage(), LENGTH_SHORT).show();
    }

    private void showSelectedCustomer() {
        if (mSelectedCustomer != null && mCustomerListAdapter != null) {
            int itemPosition = mCustomerListAdapter.getItemPosition(mSelectedCustomer);
            if (itemPosition != -1) {
                mRecyclerViewCustomers.scrollToPosition(itemPosition);
            }
        }
    }
}
