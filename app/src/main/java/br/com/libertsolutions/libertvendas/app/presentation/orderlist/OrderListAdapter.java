package br.com.libertsolutions.libertvendas.app.presentation.orderlist;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Order;
import br.com.libertsolutions.libertvendas.app.domain.pojo.OrderStatus;
import br.com.libertsolutions.libertvendas.app.presentation.base.BaseFilter;
import java.util.List;

import static android.R.color.transparent;
import static android.support.v4.content.ContextCompat.getColor;
import static android.support.v7.widget.RecyclerView.NO_POSITION;
import static android.view.LayoutInflater.from;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static br.com.libertsolutions.libertvendas.app.R.color.color_order_is_pending;
import static br.com.libertsolutions.libertvendas.app.R.color.color_order_was_cancelled;
import static br.com.libertsolutions.libertvendas.app.R.color.color_order_was_synced;
import static br.com.libertsolutions.libertvendas.app.R.layout.list_item_order;
import static br.com.libertsolutions.libertvendas.app.R.string.order_list_template_text_customer_name;
import static br.com.libertsolutions.libertvendas.app.R.string.order_list_template_text_order_date;
import static br.com.libertsolutions.libertvendas.app.R.string.order_list_template_text_order_number;
import static br.com.libertsolutions.libertvendas.app.R.string.order_list_template_text_order_total;
import static br.com.libertsolutions.libertvendas.app.R.string.orders_report_text_no_order_number;
import static br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils.formatAsCurrency;
import static br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils.formatAsDateTime;
import static br.com.libertsolutions.libertvendas.app.presentation.util.NumberUtils.withDefaultValue;

/**
 * @author Filipe Bezerra
 */
class OrderListAdapter extends RecyclerView.Adapter<OrderListViewHolder>
        implements Filterable {

    private static final int FIRST_POSITION = 0;

    private final List<Order> mOrders;

    private final boolean mShowStatusIndicator;

    private List<Order> mOrdersOriginalCopy;

    private OrderListFilter mFilter;

    OrderListAdapter(final List<Order> orders, final boolean showStatusIndicator) {
        mOrders = orders;
        mShowStatusIndicator = showStatusIndicator;
    }

    @Override public OrderListViewHolder onCreateViewHolder(
            final ViewGroup parent, final int viewType) {
        View itemView = from(parent.getContext()).inflate(list_item_order, parent, false);
        return new OrderListViewHolder(itemView);
    }

    @Override public void onBindViewHolder(final OrderListViewHolder holder, final int position) {
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

        final double totalItems = order.getTotalItems() - withDefaultValue(order.getDiscount(), 0);
        holder.textViewTotalOrder
                .setText(context.getString(order_list_template_text_order_total,
                        formatAsCurrency(totalItems)));

        holder.textViewOrderDate
                .setText(context.getString(order_list_template_text_order_date,
                        formatAsDateTime(order.getIssueDate())));

        holder.viewOrderStatus.setVisibility(mShowStatusIndicator ? VISIBLE : GONE);
        if (mShowStatusIndicator) {
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
    }

    @Override public int getItemCount() {
        return mOrders.size();
    }

    @Override public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new OrderListFilter();
        }
        return mFilter;
    }

    public boolean isEmptyList() {
        return getItemCount() == 0;
    }

    public Order getOrder(@IntRange(from = 0) int position) {
        if (position < 0 || position >= mOrders.size()) {
            return null;
        }
        return mOrders.get(position);
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

    private class OrderListFilter extends BaseFilter<Order> {

        public OrderListFilter() {
            super(OrderListAdapter.this, mOrders, mOrdersOriginalCopy);
        }

        @Override protected String[] filterValues(final Order order) {
            return new String[] { order.getCustomer().getName() };
        }
    }
}
