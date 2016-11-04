package br.com.libertsolutions.libertvendas.app.domain.factory;

import br.com.libertsolutions.libertvendas.app.domain.dto.ProdutoDto;
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

    public static List<Produto> createListProduto(List<ProdutoDto> pDtos) {
        Preconditions.checkNotNull(pDtos, "List<ProdutoDto> can't be null");

        List<Produto> produtoList = new ArrayList<>();

        for (ProdutoDto dto : pDtos) {
            produtoList.add(createProduto(dto));
        }
        return produtoList;
    }

    private static Produto createProduto(ProdutoDto pDto) {
        Preconditions.checkNotNull(
                pDto, "ProdutoDto can't be null");
        Preconditions.checkState(
                pDto.idProduto != 0, "ProdutoDto.idProduto can't be 0");
        Preconditions.checkState(
                pDto.descricao != null, "ProdutoDto.descricao can't be null");

        return new Produto(
                pDto.idProduto, pDto.codigo, pDto.codigoBarras, pDto.descricao, pDto.un,
                pDto.grupo, pDto.prcVenda, pDto.quantidade, pDto.obs, pDto.ativo,
                pDto.ultimaAlteracao
        );
    }
}
