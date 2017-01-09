package br.com.libertsolutions.libertvendas.app.presentation.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class FormasPagamentoAdapter extends SingleTextViewAdapter<FormaPagamento> {

    public FormasPagamentoAdapter(@NonNull Context context, @NonNull List<FormaPagamento> list) {
        super(context, list);
    }

    @Override protected String getText(int pPosition) {
        if (pPosition >= 0 && pPosition < getCount()) {
            return getItem(pPosition).getDescricao();
        } else {
            return "";
        }
    }
}
