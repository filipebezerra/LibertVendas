package br.com.libertsolutions.libertvendas.app.presentation.listapedidos;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.libertsolutions.libertvendas.app.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Filipe Bezerra
 */
class PedidoViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_view_numero_pedido) TextView textViewNumeroPedido;
    @BindView(R.id.text_view_nome_cliente) TextView textViewNomeCliente;
    @BindView(R.id.text_view_total_pedido) TextView textViewTotalPedido;
    @BindView(R.id.text_view_data_pedido) TextView textViewDataPedido;
    @BindView(R.id.view_status_pedido) View viewStatusPedido;

    PedidoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}