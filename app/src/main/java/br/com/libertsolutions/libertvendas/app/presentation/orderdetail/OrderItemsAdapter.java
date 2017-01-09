package br.com.libertsolutions.libertvendas.app.presentation.orderdetail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemPedido;
import br.com.libertsolutions.libertvendas.app.presentation.utils.FormattingUtils;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsViewHolder> {

    private List<ItemPedido> mOrderItems;

    private Context mContext;

    OrderItemsAdapter(@NonNull final Context context, @NonNull List<ItemPedido> orderItems) {
        mOrderItems = orderItems;
        mContext = context;
    }

    @Override public OrderItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.list_item_order_detail, parent, false);
        return new OrderItemsViewHolder(itemView);
    }

    @Override public void onBindViewHolder(OrderItemsViewHolder holder, int position) {
        final ItemPedido orderItem = mOrderItems.get(position);

        holder.textViewProduto.setText(orderItem.getProduto().getDescricao());

        holder.textViewPreco.setText(
                mContext.getString(R.string.template_text_preco,
                        FormattingUtils.formatAsDinheiro(orderItem.getPrecoVenda())));

        holder.textViewTotal.setText(
                mContext.getString(R.string.template_text_total,
                        FormattingUtils.formatAsDinheiro(orderItem.getSubTotal())));

        holder.textViewQuantidade.setText(String.valueOf(orderItem.getQuantidade()));
    }

    @Override public int getItemCount() {
        return mOrderItems.size();
    }
}
