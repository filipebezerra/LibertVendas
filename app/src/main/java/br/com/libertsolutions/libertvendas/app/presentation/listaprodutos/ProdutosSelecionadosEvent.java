package br.com.libertsolutions.libertvendas.app.presentation.listaprodutos;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Tabela;
import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class ProdutosSelecionadosEvent {

    private final List<ProdutoVo> mProdutos;

    private final Tabela mTabela;

    private ProdutosSelecionadosEvent(List<ProdutoVo> produtos, Tabela tabela) {
        mProdutos = produtos;
        mTabela = tabela;
    }

    static ProdutosSelecionadosEvent newEvent(
            List<ProdutoVo> produtos, Tabela tabela) {
        return new ProdutosSelecionadosEvent(produtos, tabela);
    }

    public List<ProdutoVo> getProdutos() {
        return mProdutos;
    }

    public Tabela getTabela() {
        return mTabela;
    }
}
