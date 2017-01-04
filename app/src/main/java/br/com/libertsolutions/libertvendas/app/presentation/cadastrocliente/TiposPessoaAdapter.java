package br.com.libertsolutions.libertvendas.app.presentation.cadastrocliente;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.domain.pojo.TipoPessoa;
import br.com.libertsolutions.libertvendas.app.presentation.widget.SingleTextViewAdapter;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
class TiposPessoaAdapter extends SingleTextViewAdapter<TipoPessoa> {

    TiposPessoaAdapter(@NonNull Context pContext, @NonNull List<TipoPessoa> pTiposPessoa) {
        super(pContext, pTiposPessoa);
    }

    @Override public long getItemId(int pPosition) {
        if (pPosition >= 0 && pPosition < getCount()) {
            return getItem(pPosition).getIntType();
        } else {
            return -1;
        }
    }

    @Override protected String getText(int pPosition) {
        if (pPosition >= 0 && pPosition < getCount()) {
            return getItem(pPosition).getDescricao();
        } else {
            return "";
        }
    }

}
