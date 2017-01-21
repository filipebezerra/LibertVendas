package br.com.libertsolutions.libertvendas.app.presentation.listapedidos;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemPedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import java.util.List;

import static br.com.libertsolutions.libertvendas.app.presentation.utils.FormattingUtils.formatAsDinheiro;
import static br.com.libertsolutions.libertvendas.app.presentation.utils.FormattingUtils.formatMillisecondsToDateText;

/**
 * @author Filipe Bezerra
 */
class OrderListAdapter extends RecyclerView.Adapter<OrderListViewHolder> {

    private final Context mContext;

    private final boolean mStatusIndicatorVisible;

    private final List<Pedido> mOrderList;

    OrderListAdapter(
            @NonNull final Context context, final boolean statusIndicatorVisible,
            @NonNull final List<Pedido> orderList) {
        mContext = context;
        mStatusIndicatorVisible = statusIndicatorVisible;
        mOrderList = orderList;
    }

    @Override public OrderListViewHolder onCreateViewHolder(
            final ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.list_item_order, parent, false);
        return new OrderListViewHolder(itemView);
    }

    @Override public void onBindViewHolder(final OrderListViewHolder holder, final int position) {
        final Pedido order = mOrderList.get(position);
        final Resources resources = mContext.getResources();

        double orderAmount = 0;
        for (ItemPedido item : order.getItens()) {
            orderAmount += item.getSubTotal();
        }
        orderAmount -= order.getDesconto();

        holder.textViewCustomerName.setText(order.getCliente().getNome());
        holder.textViewOrderAmount
                .setText(resources.getString(R.string.template_text_total_pedido,
                        formatAsDinheiro(orderAmount)));
        holder.textViewOrderDate
                .setText(resources.getString(R.string.template_text_data_pedido,
                        formatMillisecondsToDateText(order.getDataEmissao())));

        if (order.getIdPedido() != 0) {
            holder.textViewOrderNumber
                    .setText(resources.getString(R.string.template_text_numero_pedido,
                            order.getIdPedido()));
        } else {
            holder.textViewOrderNumber.setVisibility(View.GONE);
        }

        if (mStatusIndicatorVisible) {
            holder.viewOrderStatus.setVisibility(View.VISIBLE);

            int colorResource;
            switch (order.getStatus()) {
                case Pedido.STATUS_PENDENTE: {
                    colorResource = R.color.color_pedido_pendente;
                    break;
                }
                case Pedido.STATUS_ENVIADO: {
                    colorResource = R.color.color_pedido_enviado;
                    break;
                }
                case Pedido.STATUS_CANCELADO: {
                    colorResource = R.color.color_pedido_cancelado;
                    break;
                }
                default:
                    colorResource = android.R.color.transparent;
            }

            holder.viewOrderStatus
                    .setBackgroundColor(ContextCompat.getColor(mContext, colorResource));
        } else {
            holder.viewOrderStatus.setVisibility(View.GONE);
        }
    }

    @Override public int getItemCount() {
        return mOrderList.size();
    }

    void swapItems(final List<Pedido> newOrderList) {
        final OrderListDiffCallback diffCallback
                = new OrderListDiffCallback(mOrderList, newOrderList);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        mOrderList.clear();
        mOrderList.addAll(newOrderList);

        diffResult.dispatchUpdatesTo(this);
    }
}
