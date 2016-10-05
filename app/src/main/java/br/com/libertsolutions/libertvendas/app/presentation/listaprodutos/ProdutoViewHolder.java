package br.com.libertsolutions.libertvendas.app.presentation.listaprodutos;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.libertsolutions.libertvendas.app.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Filipe Bezerra
 */

class ProdutoViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_view_nome_produto) TextView textViewNomeProduto;
    @BindView(R.id.text_view_qtde_estoque) TextView textViewQtdeEstoque;
    @BindView(R.id.text_view_preco_produto) TextView textViewPrecoProduto;

    ProdutoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}