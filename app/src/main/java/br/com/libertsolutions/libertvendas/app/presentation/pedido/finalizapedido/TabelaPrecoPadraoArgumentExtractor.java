package br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizapedido;

import android.os.Bundle;
import br.com.libertsolutions.libertvendas.app.domain.pojo.TabelaPreco;
import br.com.libertsolutions.libertvendas.app.presentation.util.ExtrasExtractor;

import static br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizapedido.FinalizaPedidoFragment.ARG_TABELA_PRECO_PADRAO;

/**
 * @author Filipe Bezerra
 */
class TabelaPrecoPadraoArgumentExtractor implements ExtrasExtractor<TabelaPreco> {
    private final Bundle mData;

    TabelaPrecoPadraoArgumentExtractor(Bundle pData) {
        mData = pData;
    }

    @Override public TabelaPreco extractExtra() {
        if (mData != null && mData.containsKey(ARG_TABELA_PRECO_PADRAO)) {
            return mData.getParcelable(ARG_TABELA_PRECO_PADRAO);
        } else {
            return null;
        }
    }
}
