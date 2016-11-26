package br.com.libertsolutions.libertvendas.app.presentation.listaclientes;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.libertsolutions.libertvendas.app.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Filipe Bezerra
 */

class ClienteViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_view_razao_social) TextView textViewRazaoSocial;
    @BindView(R.id.text_view_telefone) TextView textViewTelefone;
    @BindView(R.id.text_view_email) TextView textViewEmail;
    @BindView(R.id.text_view_cpf_ou_cnpj) TextView textViewCpfOuCnpj;

    ClienteViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}