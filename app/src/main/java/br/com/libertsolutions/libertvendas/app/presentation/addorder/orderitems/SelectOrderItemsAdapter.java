package br.com.libertsolutions.libertvendas.app.presentation.addorder.orderitems;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.OrderItem;
import br.com.libertsolutions.libertvendas.app.domain.pojo.PriceTableItem;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Product;
import java.util.List;

import static br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils.formatAsCurrency;
import static br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils.formatAsNumber;
import static br.com.libertsolutions.libertvendas.app.presentation.util.NumberUtils.withDefaultValue;

/**
 * @author Filipe Bezerra
 */
class SelectOrderItemsAdapter extends RecyclerView.Adapter<SelectOrderItemsViewHolder> {

    private final List<OrderItem> mOrderItems;

    private final SelectOrderItemsCallbacks mItemsCallbacks;

    SelectOrderItemsAdapter(final List<OrderItem> orderItems,
            final SelectOrderItemsCallbacks itemsCallbacks) {
        mOrderItems = orderItems;
        mItemsCallbacks = itemsCallbacks;
    }

    @Override public SelectOrderItemsViewHolder onCreateViewHolder(
            final ViewGroup parent, final int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_order_item, parent, false);
        return new SelectOrderItemsViewHolder(itemView, mItemsCallbacks);
    }

    @Override public void onBindViewHolder(
            final SelectOrderItemsViewHolder holder, final int position) {
        final OrderItem orderItem = mOrderItems.get(position);
        final PriceTableItem item = orderItem.getItem();
        final Product product = item.getProduct();

        holder.textViewProductName.setText(product.getDescription());
        holder.textViewProductPrice.setText(
                formatAsCurrency(withDefaultValue(item.getSalesPrice(), 0)));
        holder.textViewItemTotal.setText(
                formatAsCurrency(withDefaultValue(orderItem.getSubTotal(), 0)));
        holder.textViewQuantity.setText(
                formatAsNumber(withDefaultValue(orderItem.getQuantity(), 0)));
        holder.inputLayoutEditQuantity.getEditText().setText(holder.textViewQuantity.getText());

        holder.itemView.setTag(orderItem);
    }

    @Override public int getItemCount() {
        return mOrderItems.size();
    }
}
