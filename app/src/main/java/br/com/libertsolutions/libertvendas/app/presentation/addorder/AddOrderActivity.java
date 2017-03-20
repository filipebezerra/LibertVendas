package br.com.libertsolutions.libertvendas.app.presentation.addorder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Order;
import br.com.libertsolutions.libertvendas.app.presentation.addorder.orderform.OrderFormStepFragment;
import br.com.libertsolutions.libertvendas.app.presentation.addorder.orderitems.SelectOrderItemsStepFragment;
import br.com.libertsolutions.libertvendas.app.presentation.addorder.selectcustomer.SelectCustomerStepFragment;
import br.com.libertsolutions.libertvendas.app.presentation.base.BaseStepperActivity;
import br.com.libertsolutions.libertvendas.app.presentation.customerlist.SelectedCustomerEvent;
import br.com.libertsolutions.libertvendas.app.presentation.orderlist.SelectedOrderEvent;
import com.stepstone.stepper.VerificationError;
import org.greenrobot.eventbus.Subscribe;

/**
 * @author Filipe Bezerra
 */
public class AddOrderActivity extends BaseStepperActivity {

    private Order mSelectedOrder;

    @Override protected int provideContentViewResource() {
        return R.layout.activity_add_order;
    }

    @Override protected void onCreate(@Nullable final Bundle inState) {
        super.onCreate(inState);
        setAsInitialFlowActivity();

        SelectedOrderEvent event = eventBus().getStickyEvent(SelectedOrderEvent.class);
        if (event != null) {
            mSelectedOrder = event.getOrder();
            getSupportActionBar().setTitle(R.string.add_order_editing_title);
            mStepperLayout.setCurrentStepPosition(mStepperAdapter.getCount() - 1);
        }
    }

    @Override protected void provideSteps() {
        mStepperAdapter.addStep(SelectCustomerStepFragment.newInstance(),
                getString(R.string.add_order_select_customer_step));
        mStepperAdapter.addStep(SelectOrderItemsStepFragment.newInstance(),
                getString(R.string.add_order_select_items_step));
        mStepperAdapter.addStep(OrderFormStepFragment.newInstance(),
                getString(R.string.add_order_form_step));
        mStepperLayout.setOffscreenPageLimit(mStepperAdapter.getCount());
    }

    @Override protected void onStart() {
        super.onStart();
        eventBus().register(this);
    }

    @Override protected void onStop() {
        super.onStop();
        eventBus().unregister(this);
    }

    @Override public void onCompleted(final View completeButton) {
        finish();
    }

    @Override public void onError(final VerificationError verificationError) {}

    @Subscribe public void onSelectedCustomer(SelectedCustomerEvent event) {
        mStepperLayout.proceed();
    }
}
