package br.com.libertsolutions.libertvendas.app.presentation.listapedidos;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemPedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.utils.FormattingUtils;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
class ListaPedidosAdapter extends RecyclerView.Adapter<ListaPedidosViewHolder> {

    private final Context mContext;

    private final boolean mShowStatusIndicator;

    private List<Pedido> mPedidos;

    ListaPedidosAdapter(
            @NonNull Context context, boolean showStatusIndicator, @NonNull List<Pedido> pedidos) {
        mContext = context;
        mShowStatusIndicator = showStatusIndicator;
        mPedidos = pedidos;
    }

    @Override public ListaPedidosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.list_item_pedido, parent, false);
        return new ListaPedidosViewHolder(itemView);
    }

    @Override public void onBindViewHolder(ListaPedidosViewHolder holder, int position) {
        final Pedido pedido = mPedidos.get(position);
        final Resources resources = mContext.getResources();

        holder.textViewNomeCliente.setText(
                resources.getString(R.string.template_text_nome_cliente,
                        pedido.getCliente().getNome()));

        double totalItens = 0;
        for (ItemPedido item : pedido.getItens()) {
            totalItens += item.getSubTotal();
        }
        totalItens -= pedido.getDesconto();

        holder.textViewTotalPedido.setText(
                resources.getString(R.string.template_text_total_pedido,
                        FormattingUtils.formatAsDinheiro(totalItens)));
        holder.textViewDataPedido.setText(
                resources.getString(R.string.template_text_data_pedido,
                        FormattingUtils.formatMillisecondsToDateText(pedido.getDataEmissao())));

        if (pedido.getIdPedido() != 0) {
            holder.textViewNumeroPedido.setText(
                    resources.getString(R.string.template_text_numero_pedido,
                            pedido.getIdPedido()));
        } else {
            holder.textViewNumeroPedido.setVisibility(View.GONE);
        }

        if (mShowStatusIndicator) {
            holder.viewStatusPedido.setVisibility(View.VISIBLE);

            int colorResource = -1;
            switch (pedido.getStatus()) {
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
                }
            }

            if (colorResource != -1) {
                holder.viewStatusPedido.setBackgroundColor(
                        ContextCompat.getColor(mContext, colorResource));
            }
        } else {
            holder.viewStatusPedido.setVisibility(View.GONE);
        }
    }

    @Override public int getItemCount() {
        return mPedidos.size();
    }
}
