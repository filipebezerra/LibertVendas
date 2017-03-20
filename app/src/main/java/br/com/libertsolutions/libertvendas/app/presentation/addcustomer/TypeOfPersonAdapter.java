package br.com.libertsolutions.libertvendas.app.presentation.addcustomer;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.domain.enumeration.TypeOfPerson;
import br.com.libertsolutions.libertvendas.app.presentation.widget.SingleTextViewAdapter;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
class TypeOfPersonAdapter extends SingleTextViewAdapter<TypeOfPerson> {

    TypeOfPersonAdapter(
            @NonNull final Context context, @NonNull final List<TypeOfPerson> list) {
        super(context, list);
    }

    @Override public long getItemId(int position) {
        if (position >= 0 && position < getCount()) {
            return getItem(position).getOrdinalType();
        } else {
            return -1;
        }
    }

    @Override protected String getText(final int position) {
        if (position >= 0 && position < getCount()) {
            return getItem(position).getDescription();
        } else {
            return "";
        }
    }
}
