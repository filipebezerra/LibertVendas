package br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizapedido;

import android.os.Bundle;
import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
import br.com.libertsolutions.libertvendas.app.presentation.util.ExtrasExtractor;
import java.util.List;

import static br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizapedido.FinalizaPedidoFragment.ARG_PRODUTOS_SELECIONADOS;

/**
 * @author Filipe Bezerra
 */
class ProdutosSelecionadosArgumentExtractor implements ExtrasExtractor<List<ProdutoVo>> {
    private final Bundle mData;

    ProdutosSelecionadosArgumentExtractor(Bundle pData) {
        mData = pData;
    }

    @Override public List<ProdutoVo> extractExtra() {
        if (mData != null && mData.containsKey(ARG_PRODUTOS_SELECIONADOS)) {
            return mData.getParcelableArrayList(ARG_PRODUTOS_SELECIONADOS);
        } else {
            return null;
        }
    }
}
