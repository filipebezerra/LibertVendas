package br.com.libertsolutions.libertvendas.app.presentation.pedido.listaclientes;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import br.com.libertsolutions.libertvendas.app.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Filipe Bezerra
 */
class ListaClientesViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_view_razao_social) TextView textViewRazaoSocial;
    @BindView(R.id.text_view_cpf_ou_cnpj) TextView textViewCpfOuCnpj;
    @BindView(R.id.text_view_telefone) TextView textViewTelefone;
    @BindView(R.id.text_view_email) TextView textViewEmail;

    ListaClientesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}