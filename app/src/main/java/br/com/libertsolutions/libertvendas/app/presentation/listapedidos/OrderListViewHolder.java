package br.com.libertsolutions.libertvendas.app.presentation.listapedidos;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import br.com.libertsolutions.libertvendas.app.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Filipe Bezerra
 */
class OrderListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_view_order_number) TextView textViewOrderNumber;
    @BindView(R.id.text_view_customer_name) TextView textViewCustomerName;
    @BindView(R.id.text_view_order_amount) TextView textViewOrderAmount;
    @BindView(R.id.text_view_order_date) TextView textViewOrderDate;
    @BindView(R.id.view_order_status) View viewOrderStatus;

    OrderListViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
