package br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizando;

import android.os.Bundle;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.TabelaPreco;
import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
import br.com.libertsolutions.libertvendas.app.presentation.util.TripleExtrasExtractor;
import java.util.List;

import static br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizando.FinalizandoPedidoFragment.EXTRA_ARG_CLIENTE_SELECIONADO;
import static br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizando.FinalizandoPedidoFragment.EXTRA_ARG_PRODUTOS_SELECIONADOS;
import static br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizando.FinalizandoPedidoFragment.EXTRA_ARG_TABELA_PRECO_PADRAO;

/**
 * @author Filipe Bezerra
 */
class FinalizandoPedidoExtrasExtractor
        implements TripleExtrasExtractor<List<ProdutoVo>, TabelaPreco, Cliente> {

    private final Bundle mData;

    FinalizandoPedidoExtrasExtractor(Bundle pData) {
        mData = pData;
    }

    @Override public List<ProdutoVo> extractFirstExtra() {
        if (mData != null && mData.containsKey(EXTRA_ARG_PRODUTOS_SELECIONADOS)) {
            return mData.getParcelableArrayList(EXTRA_ARG_PRODUTOS_SELECIONADOS);
        } else {
            return null;
        }
    }

    @Override public TabelaPreco extractSecondExtra() {
        if (mData != null && mData.containsKey(EXTRA_ARG_TABELA_PRECO_PADRAO)) {
            return mData.getParcelable(EXTRA_ARG_TABELA_PRECO_PADRAO);
        } else {
            return null;
        }
    }

    @Override public Cliente extractThirdExtra() {
        if (mData != null && mData.containsKey(EXTRA_ARG_CLIENTE_SELECIONADO)) {
            return mData.getParcelable(EXTRA_ARG_CLIENTE_SELECIONADO);
        } else {
            return null;
        }
    }

}
