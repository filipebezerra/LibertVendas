package br.com.libertsolutions.libertvendas.app.presentation.listaprodutos;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Filter;
import android.widget.Filterable;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
import br.com.libertsolutions.libertvendas.app.presentation.utils.AndroidUtils;
import br.com.libertsolutions.libertvendas.app.presentation.utils.FormattingUtils;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * @author Filipe Bezerra
 */
class ListaProdutosAdapter extends RecyclerView.Adapter<ListaProdutosViewHolder>
        implements Filterable {

    private final Context mContext;

    private final ListaProdutosCallbacks mCallbacks;

    private final boolean mIsSelectionMode;

    private List<ProdutoVo> mProdutos;

    private List<ProdutoVo> mProdutosOriginalCopy;

    private ListaProdutosFilter mFilter;

    ListaProdutosAdapter(
            @NonNull Context context, @NonNull ListaProdutosCallbacks callbacks,
            @NonNull List<ProdutoVo> produtos, boolean isSelectionMode) {
        mContext = context;
        mCallbacks = callbacks;
        mProdutos = produtos;
        mIsSelectionMode = isSelectionMode;
    }

    @Override public ListaProdutosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.list_item_produto, parent, false);
        return new ListaProdutosViewHolder(itemView);
    }

    @Override public void onBindViewHolder(ListaProdutosViewHolder holder, int position) {
        final ProdutoVo produto = mProdutos.get(position);

        holder.textViewProduto.setText(produto.getNome());

        holder.textViewPreco.setText(
                mContext.getString(R.string.template_text_preco,
                        FormattingUtils.formatAsDinheiro(produto.getPreco())));

        holder.textViewTotal.setText(
                mContext.getString(R.string.template_text_total,
                        FormattingUtils.formatAsDinheiro(produto.getTotalProdutos())));

        if (mIsSelectionMode) {
            holder.textViewQuantidade.setText(String.valueOf(produto.getQuantidadeAdicionada()));
            holder.editTextOutraQuantidade.setText(
                    String.valueOf(produto.getQuantidadeAdicionada()));

            holder.buttonAdicionar.setOnClickListener(
                    view -> mCallbacks.onQuantidadeAdicionada(holder.getAdapterPosition()));

            holder.buttonRemover.setOnClickListener(
                    view -> mCallbacks.onQuantidadeRemovida(holder.getAdapterPosition()));

            holder.textViewQuantidade.setOnClickListener(pView -> {
                holder.viewSwitcher.showNext();
                AndroidUtils.focusThenShowKeyboard(mContext, holder.editTextOutraQuantidade);
            });

            holder.editTextOutraQuantidade.setOnEditorActionListener(
                    (pTextView, pActionId, pEvent) -> {
                        if (pActionId == EditorInfo.IME_ACTION_DONE) {
                            AndroidUtils.hideKeyboard(mContext, holder.editTextOutraQuantidade);
                            if (!TextUtils.isEmpty(holder.editTextOutraQuantidade.getText())) {
                                float outraQuantidade = Float.valueOf(
                                        holder.editTextOutraQuantidade.getText().toString());
                                mCallbacks.onQuantidadeModificada(holder.getAdapterPosition(),
                                        outraQuantidade);
                            }
                            holder.viewSwitcher.showPrevious();
                            return true;
                        }
                        return false;
                    });

            holder.containerBotoes.setVisibility(VISIBLE);
            holder.viewSwitcher.setVisibility(VISIBLE);
            holder.textViewTotal.setVisibility(VISIBLE);
        } else {
            holder.containerBotoes.setVisibility(GONE);
            holder.viewSwitcher.setVisibility(GONE);
            holder.textViewTotal.setVisibility(GONE);
        }
    }

    @Override public int getItemCount() {
        return mProdutos.size();
    }

    @Override public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ListaProdutosFilter();
        }
        return mFilter;
    }

    private class ListaProdutosFilter extends Filter {
        @Override protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (mProdutosOriginalCopy == null) {
                mProdutosOriginalCopy = new ArrayList<>(mProdutos);
            }

            if (TextUtils.isEmpty(prefix)) {
                List<ProdutoVo> list = new ArrayList<>(mProdutosOriginalCopy);
                results.values = list;
                results.count = list.size();
            } else {
                final String prefixString = prefix.toString().toLowerCase();

                List<ProdutoVo> values = new ArrayList<>(mProdutosOriginalCopy);
                final List<ProdutoVo> newValues = new ArrayList<>();

                for(ProdutoVo produto : values) {
                    boolean contains = containsAnyProperty(prefixString,
                            produto.getNome(),
                            produto.getProduto().getCodigoBarras());

                    if (contains) {
                        newValues.add(produto);
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        private boolean containsAnyProperty(String prefix, String...properties) {
            for (String property : properties) {
                if (!TextUtils.isEmpty(property)
                        && property.trim().toLowerCase().contains(prefix)) {
                    return true;
                }
            }
            return false;
        }

        @Override protected void publishResults(CharSequence prefix, FilterResults results) {
            mProdutos.clear();
            //noinspection unchecked
            mProdutos.addAll((List<ProdutoVo>) results.values);
            notifyDataSetChanged();
        }
    }

    interface ListaProdutosCallbacks {
        void onQuantidadeAdicionada(int position);

        void onQuantidadeRemovida(int position);

        void onQuantidadeModificada(int position, float quantidade);
    }
}
