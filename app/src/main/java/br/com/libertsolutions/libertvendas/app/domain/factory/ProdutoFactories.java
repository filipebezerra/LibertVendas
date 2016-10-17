package br.com.libertsolutions.libertvendas.app.domain.factory;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Produto;
import br.com.libertsolutions.libertvendas.app.domain.util.Preconditions;
import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class ProdutoFactories {
    private ProdutoFactories() {/* No instances */}

    public static ProdutoVo createProdutoVo(Produto produto) {
        Preconditions.checkNotNull(produto, "Produto can't be null");
        return new ProdutoVo(produto);
    }

    public static List<ProdutoVo> createListProdutoVo(List<Produto> produtos) {
        Preconditions.checkNotNull(produtos, "List<Produto> can't be null");
        List<ProdutoVo> produtoVoList = new ArrayList<>();

        for (Produto produto : produtos) {
            produtoVoList.add(createProdutoVo(produto));
        }
        return produtoVoList;
    }
}
