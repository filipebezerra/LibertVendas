package br.com.libertsolutions.libertvendas.app.presentation.cadastrocliente;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cidade;
import br.com.libertsolutions.libertvendas.app.presentation.widget.SingleTextViewAdapter;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
class CidadesAdapter extends SingleTextViewAdapter<Cidade> {

    CidadesAdapter(@NonNull Context pContext, @NonNull List<Cidade> pCidades) {
        super(pContext, pCidades);
    }

    @Override protected String getText(int pPosition) {
        if (pPosition >= 0 && pPosition < getCount()) {
            return getItem(pPosition).getNome();
        } else {
            return "";
        }
    }

}
