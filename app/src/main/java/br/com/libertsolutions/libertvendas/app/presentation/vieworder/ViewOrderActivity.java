package br.com.libertsolutions.libertvendas.app.presentation.vieworder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Order;
import br.com.libertsolutions.libertvendas.app.domain.pojo.OrderStatus;
import br.com.libertsolutions.libertvendas.app.presentation.base.BaseActivity;
import br.com.libertsolutions.libertvendas.app.presentation.orderlist.SelectedOrderEvent;
import butterknife.BindView;

import static br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils.formatAsCurrency;
import static br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils.formatAsDateTime;

/**
 * @author Filipe Bezerra
 */
public class ViewOrderActivity extends BaseActivity {

    @BindView(R.id.edit_text_issue_date) EditText mEditTextIssueDate;
    @BindView(R.id.edit_text_customer_name) EditText mEditTextCustomerName;
    @BindView(R.id.edit_text_total_items) EditText mEditTextTotalItems;
    @BindView(R.id.edit_text_discount) EditText mEditTextDiscount;
    @BindView(R.id.edit_text_payment_method) EditText mEditTextPaymentMethod;
    @BindView(R.id.edit_text_observation) EditText mEditTextObservation;

    @Override protected int provideContentViewResource() {
        return R.layout.activity_view_order;
    }

    @Override protected void onCreate(@Nullable final Bundle inState) {
        super.onCreate(inState);
        setAsSubActivity();
        loadCurrentOrder();
    }

    private void loadCurrentOrder() {
        SelectedOrderEvent event = eventBus().getStickyEvent(SelectedOrderEvent.class);
        if (event != null) {
            Order order = event.getOrder();

            getSupportActionBar().setTitle(getString(R.string.title_activity_view_order,
                    order.getOrderId()));

            switch (order.getStatus()) {
                case OrderStatus.STATUS_SYNCED: {
                    getSupportActionBar().setSubtitle(R.string.order_status_synced);
                    break;
                }
                case OrderStatus.STATUS_CANCELLED: {
                    getSupportActionBar().setSubtitle(R.string.order_status_cancelled);
                    break;
                }
                case OrderStatus.STATUS_INVOICED: {
                    getSupportActionBar().setSubtitle(R.string.order_status_invoiced);
                    break;
                }
            }

            mEditTextIssueDate.setText(formatAsDateTime(order.getIssueDate()));
            mEditTextCustomerName.setText(order.getCustomer().getName());
            mEditTextTotalItems.setText(formatAsCurrency(order.getTotalItems()));
            mEditTextPaymentMethod.setText(order.getPaymentMethod().getDescription());

            if (order.getDiscount() != null) {
                mEditTextDiscount.setText(String.valueOf(order.getDiscount()));
            }

            if (!TextUtils.isEmpty(order.getObservation())) {
                mEditTextObservation.setText(order.getObservation());
            }

            eventBus().removeStickyEvent(SelectedOrderEvent.class);
        }
    }
}
