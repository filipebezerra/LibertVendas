package br.com.libertsolutions.libertvendas.app.presentation.listapedidos;

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
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils;

/**
 * @author Filipe Bezerra
 */
class PedidoAdapter extends RecyclerView.Adapter<PedidoViewHolder> implements Filterable {
    private final Context mContext;

    private List<Pedido> mPedidoList;

    private List<Pedido> mOriginalValues;

    private PedidoAdapterFilter mFilter;

    PedidoAdapter(@NonNull Context pContext, @NonNull List<Pedido> pPedidoList) {
        mContext = pContext;
        mPedidoList = pPedidoList;
    }

    @Override
    public PedidoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.list_item_pedido, parent, false);
        return new PedidoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PedidoViewHolder holder, int position) {
        final Pedido pedido = mPedidoList.get(position);
        final Resources resources = mContext.getResources();
        holder.textViewNumeroPedido.setText(
                resources.getString(R.string.template_text_numero_pedido,
                        pedido.getNumero()));
        holder.textViewNomeCliente.setText(
                resources.getString(R.string.template_text_nome_cliente,
                        pedido.getCliente().getRazaoSocial()));
        holder.textViewTotalPedido.setText(
                resources.getString(R.string.template_text_total_pedido,
                        FormattingUtils.formatAsDinheiro(pedido.getTotal())));
        holder.textViewDataPedido.setText(
                resources.getString(R.string.template_text_data_pedido,
                        FormattingUtils.formatMillisecondsToDateText(pedido.getDataEmissao())));
    }

    @Override
    public int getItemCount() {
        return mPedidoList.size();
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new PedidoAdapterFilter();
        }
        return mFilter;
    }

    private class PedidoAdapterFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (mOriginalValues == null) {
                mOriginalValues = new ArrayList<>(mPedidoList);
            }

            if (TextUtils.isEmpty(prefix)) {
                List<Pedido> list = new ArrayList<>(mOriginalValues);
                results.values = list;
                results.count = list.size();
            } else {
                final String prefixString = prefix.toString().toLowerCase();

                List<Pedido> values = new ArrayList<>(mOriginalValues);
                final List<Pedido> newValues = new ArrayList<>();

                for(Pedido pedido : values) {
                    String valueText = String.valueOf(pedido.getNumero());
                    if (!TextUtils.isEmpty(valueText) &&
                            valueText.toLowerCase().contains(prefixString)) {
                        newValues.add(pedido);
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
            mPedidoList = (List<Pedido>) results.values;
            notifyDataSetChanged();
        }
    }
}
