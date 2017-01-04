package br.com.libertsolutions.libertvendas.app.presentation.cadastropedido.finalizando;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import br.com.libertsolutions.libertvendas.app.presentation.widget.SingleTextViewAdapter;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
class FormasPagamentoAdapter extends SingleTextViewAdapter<FormaPagamento> {

    public FormasPagamentoAdapter(@NonNull Context pContext, @NonNull List<FormaPagamento> pList) {
        super(pContext, pList);
    }

    @Override protected String getText(int pPosition) {
        if (pPosition >= 0 && pPosition < getCount()) {
            return getItem(pPosition).getDescricao();
        } else {
            return "";
        }
    }
}
