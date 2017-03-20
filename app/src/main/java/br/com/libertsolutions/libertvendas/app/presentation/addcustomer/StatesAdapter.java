package br.com.libertsolutions.libertvendas.app.presentation.addcustomer;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.domain.pojo.State;
import br.com.libertsolutions.libertvendas.app.presentation.widget.SingleTextViewAdapter;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
class StatesAdapter extends SingleTextViewAdapter<State> {

    StatesAdapter(@NonNull final Context context, @NonNull final List<State> list) {
        super(context, list);
    }

    @Override public long getItemId(final int position) {
        if (position >= 0 && position < getCount()) {
            return getItem(position).getStateId();
        } else {
            return -1;
        }
    }

    @Override protected String getText(final int position) {
        if (position >= 0 && position < getCount()) {
            return getItem(position).getName();
        } else {
            return "";
        }
    }
}
