package br.com.libertsolutions.libertvendas.app.presentation.orderdetail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import br.com.libertsolutions.libertvendas.app.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Filipe Bezerra
 */
class OrderItemsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_view_produto) TextView textViewProduto;
    @BindView(R.id.text_view_preco) TextView textViewPreco;
    @BindView(R.id.text_view_total) TextView textViewTotal;
    @BindView(R.id.text_view_quantidade) TextView textViewQuantidade;

    OrderItemsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
