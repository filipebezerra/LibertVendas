package br.com.libertsolutions.libertvendas.app.presentation.listaprodutos;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Produto;
import br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils;

/**
 * @author Filipe Bezerra
 */

class ProdutoAdapter extends RecyclerView.Adapter<ProdutoViewHolder> implements Filterable {
    private final Context mContext;

    private List<Produto> mProdutoList;

    private List<Produto> mOriginalValues;

    private ProdutoAdapterFilter mFilter;

    ProdutoAdapter(@NonNull Context pContext, @NonNull List<Produto> pProdutoList) {
        mContext = pContext;
        mProdutoList = pProdutoList;
    }

    @Override
    public ProdutoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.list_item_produto, parent, false);
        return new ProdutoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProdutoViewHolder holder, int position) {
        final Produto produto = mProdutoList.get(position);
        final Resources resources = mContext.getResources();
        holder.textViewNomeProduto.setText(produto.getNome());
        holder.textViewQtdeEstoque.setText(
                resources.getString(R.string.template_text_qtde_estoque,
                        FormattingUtils.formatAsQuantidade(produto.getQuantidadeEstoque())));
        holder.textViewPrecoProduto.setText(
                FormattingUtils.formatAsDinheiro(produto.getPreco()));
    }

    @Override
    public int getItemCount() {
        return mProdutoList.size();
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ProdutoAdapterFilter();
        }
        return mFilter;
    }

    private class ProdutoAdapterFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (mOriginalValues == null) {
                mOriginalValues = new ArrayList<>(mProdutoList);
            }

            if (TextUtils.isEmpty(prefix)) {
                List<Produto> list = new ArrayList<>(mOriginalValues);
                results.values = list;
                results.count = list.size();
            } else {
                final String prefixString = prefix.toString().toLowerCase();

                List<Produto> values = new ArrayList<>(mOriginalValues);
                final List<Produto> newValues = new ArrayList<>();

                for(Produto produto : values) {
                    String valueText = produto.getNome();
                    if (!TextUtils.isEmpty(valueText) &&
                            valueText.toLowerCase().contains(prefixString)) {
                        newValues.add(produto);
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence prefix, FilterResults results) {
            //noinspection unchecked
            mProdutoList = (List<Produto>) results.values;
            notifyDataSetChanged();
        }
    }
}
