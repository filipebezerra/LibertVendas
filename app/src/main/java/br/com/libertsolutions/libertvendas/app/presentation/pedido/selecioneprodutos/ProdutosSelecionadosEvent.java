package br.com.libertsolutions.libertvendas.app.presentation.pedido.selecioneprodutos;

import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class ProdutosSelecionadosEvent {
    private final List<ProdutoVo> mProdutoVoList;

    private ProdutosSelecionadosEvent(List<ProdutoVo> pProdutoVoList) {
        mProdutoVoList = pProdutoVoList;
    }

    public static ProdutosSelecionadosEvent newEvent(List<ProdutoVo> pProdutoVoList) {
        return new ProdutosSelecionadosEvent(pProdutoVoList);
    }

    public List<ProdutoVo> getProdutoVoList() {
        return mProdutoVoList;
    }
}
