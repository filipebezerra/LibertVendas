package br.com.libertsolutions.libertvendas.app.presentation.importation;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import br.com.libertsolutions.libertvendas.app.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dd.CircularProgressButton;

/**
 * @author Filipe Bezerra
 */
class ImportationViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_view_importation_company_name) TextView textViewCompanyName;
    @BindView(R.id.button_importation_importation_progress) CircularProgressButton progressButton;
    @BindView(R.id.button_all_retry) Button buttonRetry;

    ImportationViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
