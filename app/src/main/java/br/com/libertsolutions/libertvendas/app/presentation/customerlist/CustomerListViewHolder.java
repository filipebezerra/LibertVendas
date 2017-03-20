package br.com.libertsolutions.libertvendas.app.presentation.customerlist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import br.com.libertsolutions.libertvendas.app.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Filipe Bezerra
 */
class CustomerListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_view_social_name) TextView textViewSocialName;
    @BindView(R.id.text_view_fantasy_name) TextView textViewFantasyName;
    @BindView(R.id.text_view_cpf_or_cnpj) TextView textViewCpfOrCnpj;
    @BindView(R.id.text_view_phone) TextView textViewPhone;
    @BindView(R.id.text_view_email) TextView textViewEmail;

    CustomerListViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
