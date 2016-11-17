package br.com.libertsolutions.libertvendas.app.presentation.home;

import android.content.Intent;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.util.ExtrasExtractor;

import static br.com.libertsolutions.libertvendas.app.presentation.pedido.PedidoActivity.EXTRA_RESULT_NEW_PEDIDO;

/**
 * @author Filipe Bezerra
 */
class PedidoExtrasExtractor implements ExtrasExtractor<Pedido> {
    private final Intent mData;

    PedidoExtrasExtractor(Intent pData) {
        mData = pData;
    }

    @Override
    public Pedido extractExtra() {
        if (mData != null && mData.hasExtra(EXTRA_RESULT_NEW_PEDIDO)) {
            return mData.getParcelableExtra(EXTRA_RESULT_NEW_PEDIDO);
        } else {
            return null;
        }
    }
}
