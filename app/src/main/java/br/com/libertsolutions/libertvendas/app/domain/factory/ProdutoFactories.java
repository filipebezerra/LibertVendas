package br.com.libertsolutions.libertvendas.app.domain.factory;

import br.com.libertsolutions.libertvendas.app.domain.dto.ProdutoDto;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemTabela;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Produto;
import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class ProdutoFactories {

    private ProdutoFactories() {/* No instances */}

    public static ProdutoVo createProdutoVo(ItemTabela itemTabela) {
        return ProdutoVo.create(itemTabela);
    }

    public static List<ProdutoVo> createListProdutoVo(List<ItemTabela> itensTabela) {
        List<ProdutoVo> produtoVoList = new ArrayList<>();

        for (ItemTabela itemTabela : itensTabela) {
            produtoVoList.add(createProdutoVo(itemTabela));
        }
        return produtoVoList;
    }

    static Produto createProduto(ProdutoDto pDto) {
        return Produto.create(
                pDto.idProduto, pDto.codigo, pDto.codigoBarras, pDto.descricao, pDto.un,
                pDto.grupo, pDto.quantidade, pDto.obs, pDto.ativo,
                pDto.ultimaAlteracao
        );
    }
}
