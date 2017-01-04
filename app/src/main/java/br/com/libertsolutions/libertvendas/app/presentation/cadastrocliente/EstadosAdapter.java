package br.com.libertsolutions.libertvendas.app.presentation.cadastrocliente;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Estado;
import br.com.libertsolutions.libertvendas.app.presentation.widget.SingleTextViewAdapter;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
class EstadosAdapter extends SingleTextViewAdapter<Estado> {

    EstadosAdapter(@NonNull Context pContext, @NonNull List<Estado> pEstados) {
        super(pContext, pEstados);
    }

    @Override protected String getText(int pPosition) {
        if (pPosition >= 0 && pPosition < getCount()) {
            return getItem(pPosition).getNome();
        } else {
            return "";
        }
    }

}
