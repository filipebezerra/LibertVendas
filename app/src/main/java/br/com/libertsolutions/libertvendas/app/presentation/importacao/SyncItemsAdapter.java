package br.com.libertsolutions.libertvendas.app.presentation.importacao;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Empresa;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
class SyncItemsAdapter extends RecyclerView.Adapter<SyncItemsViewHolder> {

    private final Context mContext;
    private final List<Empresa> mSyncItems;

    SyncItemsAdapter(final Context context, final List<Empresa> syncItems) {
        mContext = context;
        mSyncItems = syncItems;
    }

    @Override public SyncItemsViewHolder onCreateViewHolder(
            final ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.list_item_sync, parent, false);
        return new SyncItemsViewHolder(itemView);
    }

    @Override public void onBindViewHolder(final SyncItemsViewHolder holder, final int position) {}

    @Override public void onBindViewHolder(
            final SyncItemsViewHolder holder, final int position, final List<Object> payloads) {
        if (payloads.isEmpty()) {
            Empresa empresa = mSyncItems.get(position);
            holder.textViewSyncIdentifier.setText(empresa.getNome());
            holder.buttonSyncState.setIndeterminateProgressMode(true);
            holder.buttonSyncState.setProgress(50);
        } else {
            if (payloads.get(0) == SyncState.FAILED) {
                holder.buttonSyncState.setProgress(-1);
            } else {
                holder.buttonSyncState.setProgress(100);
            }
        }
    }

    @Override public int getItemCount() {
        return mSyncItems.size();
    }

    void showError(final Empresa syncItem) {
        int syncItemPosition = mSyncItems.indexOf(syncItem);
        if (syncItemPosition != -1) {
            notifyItemChanged(syncItemPosition, SyncState.FAILED);
        }
    }

    void showDone(final Empresa syncItem) {
        int syncItemPosition = mSyncItems.indexOf(syncItem);
        if (syncItemPosition != -1) {
            notifyItemChanged(syncItemPosition, SyncState.COMPLETED);
        }
    }

    private enum SyncState {
        FAILED, COMPLETED
    }
}
