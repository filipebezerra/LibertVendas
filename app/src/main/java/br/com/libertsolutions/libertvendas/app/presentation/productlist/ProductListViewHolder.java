package br.com.libertsolutions.libertvendas.app.presentation.productlist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import br.com.libertsolutions.libertvendas.app.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Filipe Bezerra
 */
class ProductListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_view_product_name) TextView textViewName;
    @BindView(R.id.text_view_product_price) TextView textViewPrice;
    @BindView(R.id.text_view_stock_quantity) TextView textViewStockQuantity;

    ProductListViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
