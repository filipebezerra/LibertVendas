package br.com.libertsolutions.libertvendas.app.presentation.ordersreport;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Order;
import br.com.libertsolutions.libertvendas.app.domain.pojo.OrderStatus;
import java.util.List;

import static android.R.color.transparent;
import static android.support.v4.content.ContextCompat.getColor;
import static android.support.v7.widget.RecyclerView.NO_POSITION;
import static android.view.LayoutInflater.from;
import static br.com.libertsolutions.libertvendas.app.R.color.color_order_is_pending;
import static br.com.libertsolutions.libertvendas.app.R.color.color_order_was_cancelled;
import static br.com.libertsolutions.libertvendas.app.R.color.color_order_was_synced;
import static br.com.libertsolutions.libertvendas.app.R.layout.list_item_order_report;
import static br.com.libertsolutions.libertvendas.app.R.string.order_list_template_text_order_date;
import static br.com.libertsolutions.libertvendas.app.R.string.order_list_template_text_order_number;
import static br.com.libertsolutions.libertvendas.app.R.string.order_list_template_text_order_total;
import static br.com.libertsolutions.libertvendas.app.R.string.orders_report_text_no_order_number;
import static br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils.formatAsCurrency;
import static br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils.formatAsDate;
import static br.com.libertsolutions.libertvendas.app.presentation.util.NumberUtils.withDefaultValue;

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

        holder.textViewCustomerName.setText(order.getCustomer().getName());

        final double totalItems = order.getTotalItems() - withDefaultValue(order.getDiscount(), 0);
        holder.textViewTotalOrder
                .setText(context.getString(order_list_template_text_order_total,
                        formatAsCurrency(totalItems)));

        holder.textViewOrderDate
                .setText(context.getString(order_list_template_text_order_date,
                        formatAsDate(order.getIssueDate())));

        int colorResource;
        switch (order.getStatus()) {
            case OrderStatus.STATUS_CREATED:
            case OrderStatus.STATUS_MODIFIED: {
                colorResource = color_order_is_pending;
                break;
            }

            case OrderStatus.STATUS_SYNCED: {
                colorResource = color_order_was_synced;
                break;
            }

            case OrderStatus.STATUS_CANCELLED: {
                colorResource = color_order_was_cancelled;
                break;
            }

            default:
                colorResource = transparent;
        }
        holder.viewOrderStatus.setBackgroundColor(getColor(context, colorResource));
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
