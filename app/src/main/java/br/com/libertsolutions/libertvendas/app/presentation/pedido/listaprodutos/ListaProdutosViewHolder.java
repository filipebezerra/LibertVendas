package br.com.libertsolutions.libertvendas.app.presentation.pedido.listaprodutos;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import br.com.libertsolutions.libertvendas.app.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Filipe Bezerra
 */
class ListaProdutosViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.card_view) CardView cardContainer;
    @BindView(R.id.text_view_produto) TextView textViewProduto;
    @BindView(R.id.text_view_preco) TextView textViewPreco;
    @BindView(R.id.text_view_total) TextView textViewTotal;
    @BindView(R.id.text_view_quantidade) TextView textViewQuantidade;
    @BindView(R.id.button_adicionar) ImageButton buttonAdicionar;
    @BindView(R.id.button_remover) ImageButton buttonRemover;
    @BindView(R.id.view_switcher) ViewSwitcher viewSwitcher;
    @BindView(R.id.edit_text_edit_quantidade) EditText editTextOutraQuantidade;

    ListaProdutosViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
