package br.com.libertsolutions.libertvendas.app.presentation.vieworder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Order;
import br.com.libertsolutions.libertvendas.app.domain.pojo.OrderStatus;
import br.com.libertsolutions.libertvendas.app.presentation.base.BaseActivity;
import br.com.libertsolutions.libertvendas.app.presentation.orderlist.SelectedOrderEvent;
import butterknife.BindView;

import static android.text.TextUtils.isEmpty;
import static br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils.formatAsCurrency;
import static br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils.formatAsDateTime;
import static br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils.formatAsPercent;

/**
 * @author Filipe Bezerra
 */
public class ViewOrderActivity extends BaseActivity {

    @BindView(R.id.edit_text_issue_date) EditText editTextIssueDate;
    @BindView(R.id.edit_text_customer_name) EditText editTextCustomerName;
    @BindView(R.id.edit_text_total_items) EditText editTextTotalItems;
    @BindView(R.id.input_layout_payment_method) TextInputLayout inputLayoutPaymentMethod;
    @BindView(R.id.edit_text_discount_percentage) EditText editTextDiscountPercentage;
    @BindView(R.id.edit_text_total_order) EditText editTextTotalOrder;
    @BindView(R.id.edit_text_observation) EditText editTextObservation;

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

            editTextIssueDate.setText(formatAsDateTime(order.getIssueDate()));
            editTextCustomerName.setText(order.getCustomer().getName());
            editTextTotalItems.setText(formatAsCurrency(order.getTotalItems()));
            inputLayoutPaymentMethod.getEditText()
                    .setText(order.getPaymentMethod().getDescription());

            if (order.getPaymentMethod().getDiscountPercentage() == 0) {
                inputLayoutPaymentMethod.setHint(
                        getString(R.string.order_form_payment_method_label,
                                getString(R.string.order_form_no_discount)));
            } else {
                inputLayoutPaymentMethod.setHint(
                        getString(R.string.order_form_payment_method_label,
                                formatAsPercent(order.getPaymentMethod().getDiscountPercentage())));
            }

            editTextDiscountPercentage.setText(formatAsPercent(order.getDiscountPercentage()));
            editTextTotalOrder.setText(formatAsCurrency(order.getTotalOrder()));

            if (!isEmpty(order.getObservation())) {
                editTextObservation.setText(order.getObservation());
            }

            eventBus().removeStickyEvent(SelectedOrderEvent.class);
        }
    }
}
