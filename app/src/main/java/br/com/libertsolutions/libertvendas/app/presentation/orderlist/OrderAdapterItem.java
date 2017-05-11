package br.com.libertsolutions.libertvendas.app.presentation.orderlist;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Order;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mikepenz.fastadapter.commons.utils.FastAdapterUIUtils;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.mikepenz.materialize.util.UIUtils;
import java.util.List;

import static android.R.color.transparent;
import static android.support.v4.content.ContextCompat.getColor;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static br.com.libertsolutions.libertvendas.app.R.id.order_item;
import static br.com.libertsolutions.libertvendas.app.R.id.text_view_customer_name;
import static br.com.libertsolutions.libertvendas.app.R.id.text_view_order_date;
import static br.com.libertsolutions.libertvendas.app.R.id.text_view_order_number;
import static br.com.libertsolutions.libertvendas.app.R.id.text_view_total_order;
import static br.com.libertsolutions.libertvendas.app.R.id.view_order_status;
import static br.com.libertsolutions.libertvendas.app.R.layout.list_item_order;
import static br.com.libertsolutions.libertvendas.app.R.string.order_list_template_text_customer_name;
import static br.com.libertsolutions.libertvendas.app.R.string.order_list_template_text_order_date;
import static br.com.libertsolutions.libertvendas.app.R.string.order_list_template_text_order_number;
import static br.com.libertsolutions.libertvendas.app.R.string.order_list_template_text_order_total;
import static br.com.libertsolutions.libertvendas.app.R.string.orders_report_text_no_order_number;
import static br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils.formatAsCurrency;
import static br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils.formatAsDateTime;
import static br.com.libertsolutions.libertvendas.app.presentation.util.NumberUtils.withDefaultValue;
import static br.com.libertsolutions.libertvendas.app.presentation.util.OrderUtils.getStatusColor;

/**
 * @author Filipe Bezerra
 */
class OrderAdapterItem extends AbstractItem<OrderAdapterItem, OrderAdapterItem.ViewHolder> {

    private final boolean showStatusIndicator;
    private Order order;

    private OrderAdapterItem(final boolean showStatusIndicator) {
        this.showStatusIndicator = showStatusIndicator;
    }

    static OrderAdapterItem create(final boolean showStatusIndicator) {
        return new OrderAdapterItem(showStatusIndicator);
    }

    public OrderAdapterItem withOrder(final Order order) {
        this.order = order;
        return this;
    }

    public Order getOrder() {
        return order;
    }

    @Override public ViewHolder getViewHolder(final View v) {
        return new ViewHolder(v);
    }

    @Override public int getType() {
        return order_item;
    }

    @Override public int getLayoutRes() {
        return list_item_order;
    }

    @Override public void bindView(final ViewHolder holder, final List<Object> payloads) {
        super.bindView(holder, payloads);

        final Context context = holder.itemView.getContext();

        if (order.getOrderId() != null && order.getOrderId() != 0) {
            holder.textViewOrderNumber
                    .setText(context.getString(order_list_template_text_order_number,
                            order.getOrderId()));
        } else {
            holder.textViewOrderNumber
                    .setText(context.getString(orders_report_text_no_order_number));
        }

        holder.textViewCustomerName
                .setText(context.getString(order_list_template_text_customer_name,
                        order.getCustomer().getName()));

        final double totalItems = order.getTotalItems() - withDefaultValue(order.getDiscount(), 0);
        holder.textViewTotalOrder
                .setText(context.getString(order_list_template_text_order_total,
                        formatAsCurrency(totalItems)));

        holder.textViewOrderDate
                .setText(context.getString(order_list_template_text_order_date,
                        formatAsDateTime(order.getIssueDate())));

        holder.viewOrderStatus.setVisibility(showStatusIndicator ? VISIBLE : GONE);
        if (showStatusIndicator) {
            holder.viewOrderStatus
                    .setBackgroundColor(getColor(context, getStatusColor(order.getStatus())));
        }

        UIUtils.setBackground(holder.itemView, FastAdapterUIUtils.getSelectableBackground(context,
                ContextCompat.getColor(context, R.color.color_accent), true));
    }

    @Override public void unbindView(final ViewHolder holder) {
        super.unbindView(holder);
        holder.textViewOrderNumber.setText(null);
        holder.textViewCustomerName.setText(null);
        holder.textViewTotalOrder.setText(null);
        holder.textViewOrderDate.setText(null);
        holder.viewOrderStatus
                .setBackgroundColor(getColor(holder.itemView.getContext(), transparent));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(text_view_order_number) TextView textViewOrderNumber;
        @BindView(text_view_customer_name) TextView textViewCustomerName;
        @BindView(text_view_total_order) TextView textViewTotalOrder;
        @BindView(text_view_order_date) TextView textViewOrderDate;
        @BindView(view_order_status) View viewOrderStatus;

        ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
