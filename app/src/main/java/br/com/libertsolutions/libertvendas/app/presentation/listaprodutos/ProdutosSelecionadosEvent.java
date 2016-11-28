package br.com.libertsolutions.libertvendas.app.presentation.listaprodutos;

import br.com.libertsolutions.libertvendas.app.domain.pojo.TabelaPreco;
import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class ProdutosSelecionadosEvent {
    private final List<ProdutoVo> mProdutoVoList;

    private final TabelaPreco mTabelaPreco;

    private ProdutosSelecionadosEvent(
            List<ProdutoVo> pProdutoVoList, TabelaPreco pMTabelaPreco) {
        mProdutoVoList = pProdutoVoList;
        mTabelaPreco = pMTabelaPreco;
    }

    public static ProdutosSelecionadosEvent newEvent(
            List<ProdutoVo> pProdutoVoList, TabelaPreco pTabelaPrecoPadrao) {
        return new ProdutosSelecionadosEvent(pProdutoVoList, pTabelaPrecoPadrao);
    }

    public List<ProdutoVo> getProdutoVoList() {
        return mProdutoVoList;
    }

    public TabelaPreco getTabelaPreco() {
        return mTabelaPreco;
    }
}
