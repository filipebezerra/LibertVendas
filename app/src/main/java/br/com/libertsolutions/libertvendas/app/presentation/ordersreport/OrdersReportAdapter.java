package br.com.libertsolutions.libertvendas.app.presentation.ordersreport;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Order;
import java.util.List;

import static android.support.v4.content.ContextCompat.getColor;
import static android.support.v7.widget.RecyclerView.NO_POSITION;
import static android.view.LayoutInflater.from;
import static br.com.libertsolutions.libertvendas.app.R.layout.list_item_order_report;
import static br.com.libertsolutions.libertvendas.app.R.string.order_list_template_text_customer_name;
import static br.com.libertsolutions.libertvendas.app.R.string.order_list_template_text_order_date;
import static br.com.libertsolutions.libertvendas.app.R.string.order_list_template_text_order_number;
import static br.com.libertsolutions.libertvendas.app.R.string.order_list_template_text_order_total;
import static br.com.libertsolutions.libertvendas.app.R.string.orders_report_text_no_order_number;
import static br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils.formatAsCurrency;
import static br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils.formatAsDateTime;
import static br.com.libertsolutions.libertvendas.app.presentation.util.OrderUtils.getStatusColor;

/**
 * @author Filipe Bezerra
 */
class OrdersReportAdapter extends RecyclerView.Adapter<OrdersReportViewHolder> {

    private static final int FIRST_POSITION = 0;

    private final List<Order> mOrders;

    OrdersReportAdapter(final List<Order> orders) {
        mOrders = orders;
    }

    @Override public OrdersReportViewHolder onCreateViewHolder(
            final ViewGroup parent, final int viewType) {
        View itemView = from(parent.getContext()).inflate(list_item_order_report, parent, false);
        return new OrdersReportViewHolder(itemView);
    }

    @Override public void onBindViewHolder(final OrdersReportViewHolder holder, final int position) {
        final Order order = mOrders.get(position);
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

        final double totalItems = order.getTotalOrder();
        holder.textViewTotalOrder
                .setText(context.getString(order_list_template_text_order_total,
                        formatAsCurrency(totalItems)));

        holder.textViewOrderDate
                .setText(context.getString(order_list_template_text_order_date,
                        formatAsDateTime(order.getIssueDate())));

        holder.viewOrderStatus
                .setBackgroundColor(getColor(context, getStatusColor(order.getStatus())));
    }

    @Override public int getItemCount() {
        return mOrders.size();
    }

    public int updateOrder(final Order order) {
        if (order != null) {
            final int currentPosition = mOrders.indexOf(order);
            if (currentPosition == NO_POSITION) {
                mOrders.add(FIRST_POSITION, order);
                notifyItemInserted(FIRST_POSITION);
                return FIRST_POSITION;
            } else {
                mOrders.set(currentPosition, order);
                notifyItemChanged(currentPosition);
                return currentPosition;
            }
        }
        return NO_POSITION;
    }
}
