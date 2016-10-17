package br.com.libertsolutions.libertvendas.app.presentation.pedido.selecioneprodutos;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
import br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
class ProdutoSelecionadoAdapter extends RecyclerView.Adapter<ProdutoSelecionadoViewHolder>
        implements Filterable {

    private final Context mContext;

    private final ProdutoSelecionadoAdapterCallbacks mCallbacks;

    private List<ProdutoVo> mProdutoList;

    private List<ProdutoVo> mOriginalValues;

    private ProdutoAdapterFilter mFilter;

    ProdutoSelecionadoAdapter(@NonNull Context pContext,
            @NonNull ProdutoSelecionadoAdapterCallbacks pCallbacks,
            @NonNull List<ProdutoVo> pProdutoList) {
        mContext = pContext;
        mCallbacks = pCallbacks;
        mProdutoList = pProdutoList;
    }

    @Override
    public ProdutoSelecionadoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.list_item_selecione_produto, parent, false);
        return new ProdutoSelecionadoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProdutoSelecionadoViewHolder holder, int position) {
        final ProdutoVo produto = mProdutoList.get(position);

        holder.textViewProduto.setText(produto.getNome());

        holder.textViewPreco.setText(
                mContext.getString(R.string.template_text_preco,
                        FormattingUtils.formatAsDinheiro(produto.getPreco())));

        holder.textViewTotal.setText(
                mContext.getString(R.string.template_text_total,
                        FormattingUtils.formatAsDinheiro(produto.getTotalProdutos())));

        holder.textViewQuantidade.setText(String.valueOf(produto.getQuantidadeAdicionada()));

        holder.buttonAdicionar.setOnClickListener(
                view -> mCallbacks.onQuantidadeAdicionada(holder.getAdapterPosition()));

        holder.buttonRemover.setOnClickListener(
                view -> mCallbacks.onQuantidadeRemovida(holder.getAdapterPosition()));
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
                List<ProdutoVo> list = new ArrayList<>(mOriginalValues);
                results.values = list;
                results.count = list.size();
            } else {
                final String prefixString = prefix.toString().toLowerCase();

                List<ProdutoVo> values = new ArrayList<>(mOriginalValues);
                final List<ProdutoVo> newValues = new ArrayList<>();

                for(ProdutoVo produto : values) {
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
            mProdutoList = (List<ProdutoVo>) results.values;
            notifyDataSetChanged();
        }
    }

    interface ProdutoSelecionadoAdapterCallbacks {
        void onQuantidadeAdicionada(int position);

        void onQuantidadeRemovida(int position);
    }
}
