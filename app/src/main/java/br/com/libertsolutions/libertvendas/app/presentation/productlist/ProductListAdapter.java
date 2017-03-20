package br.com.libertsolutions.libertvendas.app.presentation.productlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.PriceTableItem;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Product;
import br.com.libertsolutions.libertvendas.app.presentation.base.BaseFilter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils.formatAsCurrency;
import static br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils.formatAsNumber;
import static br.com.libertsolutions.libertvendas.app.presentation.util.NumberUtils.withDefaultValue;

/**
 * @author Filipe Bezerra
 */
class ProductListAdapter extends RecyclerView.Adapter<ProductListViewHolder>
        implements Filterable {

    private final List<PriceTableItem> mPriceTableItems;

    private List<PriceTableItem> mPriceTableItemsOriginalCopy;

    private ProductListFilter mFilter;

    ProductListAdapter(final List<PriceTableItem> priceTableItems) {
        mPriceTableItems = priceTableItems;
        Collections.sort(mPriceTableItems, new Comparator<PriceTableItem>() {
            @Override public int compare(final PriceTableItem item1, final PriceTableItem item2) {
                return item1.getProduct().getDescription()
                        .compareToIgnoreCase(item2.getProduct().getDescription());
            }
        });
    }

    @Override public ProductListViewHolder onCreateViewHolder(
            final ViewGroup parent, final int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_product, parent, false);
        return new ProductListViewHolder(itemView);
    }

    @Override public void onBindViewHolder(final ProductListViewHolder holder, final int position) {
        final PriceTableItem priceTableItem = mPriceTableItems.get(position);
        final Product product = priceTableItem.getProduct();
        final Context context = holder.itemView.getContext();

        holder.textViewName.setText(product.getDescription());
        holder.textViewPrice.setText(
                context.getString(R.string.product_list_template_product_price,
                        formatAsCurrency(priceTableItem.getSalesPrice())));
        holder.textViewStockQuantity.setText(
                context.getString(R.string.product_list_template_product_stock_quantity,
                        formatAsNumber(withDefaultValue(product.getStockQuantity(), 0))));
    }

    @Override public int getItemCount() {
        return mPriceTableItems.size();
    }

    @Override public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ProductListFilter();
        }
        return mFilter;
    }

    boolean isEmptyList() {
        return getItemCount() == 0;
    }

    private class ProductListFilter extends BaseFilter<PriceTableItem> {

        ProductListFilter() {
            super(ProductListAdapter.this, mPriceTableItems, mPriceTableItemsOriginalCopy);
        }

        @Override protected String[] filterValues(final PriceTableItem priceTableItem) {
            final Product product = priceTableItem.getProduct();
            return new String[] { product.getDescription(), product.getBarCode()};
        }
    }
}
