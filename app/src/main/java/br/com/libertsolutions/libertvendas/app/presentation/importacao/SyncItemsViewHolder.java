package br.com.libertsolutions.libertvendas.app.presentation.importacao;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import br.com.libertsolutions.libertvendas.app.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dd.CircularProgressButton;

/**
 * @author Filipe Bezerra
 */
class SyncItemsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_view_sync_identifier) TextView textViewSyncIdentifier;
    @BindView(R.id.button_sync_state) CircularProgressButton buttonSyncState;

    SyncItemsViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
