package br.com.libertsolutions.libertvendas.app.presentation.citylist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import br.com.libertsolutions.libertvendas.app.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Filipe Bezerra
 */
class CityListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_view_city_name) TextView textViewName;

    CityListViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
