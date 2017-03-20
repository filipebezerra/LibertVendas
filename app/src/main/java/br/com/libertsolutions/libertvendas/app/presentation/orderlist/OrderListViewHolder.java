package br.com.libertsolutions.libertvendas.app.presentation.orderlist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static br.com.libertsolutions.libertvendas.app.R.id.text_view_customer_name;
import static br.com.libertsolutions.libertvendas.app.R.id.text_view_order_date;
import static br.com.libertsolutions.libertvendas.app.R.id.text_view_order_number;
import static br.com.libertsolutions.libertvendas.app.R.id.text_view_total_order;
import static br.com.libertsolutions.libertvendas.app.R.id.view_order_status;

/**
 * @author Filipe Bezerra
 */
class OrderListViewHolder extends RecyclerView.ViewHolder {

    @BindView(text_view_order_number) TextView textViewOrderNumber;
    @BindView(text_view_customer_name) TextView textViewCustomerName;
    @BindView(text_view_total_order) TextView textViewTotalOrder;
    @BindView(text_view_order_date) TextView textViewOrderDate;
    @BindView(view_order_status) View viewOrderStatus;

    OrderListViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
