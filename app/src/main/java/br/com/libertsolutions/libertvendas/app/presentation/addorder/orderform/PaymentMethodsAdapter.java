package br.com.libertsolutions.libertvendas.app.presentation.addorder.orderform;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.domain.pojo.PaymentMethod;
import br.com.libertsolutions.libertvendas.app.presentation.widget.SingleTextViewAdapter;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
class PaymentMethodsAdapter extends SingleTextViewAdapter<PaymentMethod> {

    PaymentMethodsAdapter(@NonNull final Context context, @NonNull final List<PaymentMethod> list) {
        super(context, list);
    }

    @Override public long getItemId(final int position) {
        if (position >= 0 && position < getCount()) {
            return getItem(position).getPaymentMethodId();
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
