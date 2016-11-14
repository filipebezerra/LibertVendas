package br.com.libertsolutions.libertvendas.app.domain.factory;

import android.support.v4.util.Pair;
import br.com.libertsolutions.libertvendas.app.domain.dto.ProdutoDto;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemTabela;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Produto;
import br.com.libertsolutions.libertvendas.app.domain.util.Preconditions;
import br.com.libertsolutions.libertvendas.app.domain.vo.ProdutoVo;
import br.com.libertsolutions.libertvendas.app.presentation.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class ProdutoFactories {
    private ProdutoFactories() {/* No instances */}

    public static ProdutoVo createProdutoVo(Pair<Produto, ItemTabela> pProdutoItemTabela) {
        Preconditions.checkNotNull(pProdutoItemTabela,
                "Pair<Produto, ItemTabela> can't be null");
        Preconditions.checkNotNull(pProdutoItemTabela.first,
                "Produto can't be null");
        Preconditions.checkNotNull(pProdutoItemTabela.second,
                "ItemTabela can't be null");
        return new ProdutoVo(pProdutoItemTabela.first, pProdutoItemTabela.second);
    }

    public static List<ProdutoVo> createListProdutoVo(
            List<Pair<Produto, ItemTabela>> pProdutosItemTabela) {
        Preconditions.checkNotNull(pProdutosItemTabela,
                "List<Pair<Produto, ItemTabela>> can't be null");
        List<ProdutoVo> produtoVoList = new ArrayList<>();

        for (Pair<Produto, ItemTabela> produtoItemTabela : pProdutosItemTabela) {
            produtoVoList.add(createProdutoVo(produtoItemTabela));
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
                !StringUtils.isEmpty(pDto.descricao), "ProdutoDto.descricao can't be empty");

        return new Produto(
                pDto.idProduto, pDto.codigo, pDto.codigoBarras, pDto.descricao, pDto.un,
                pDto.grupo, pDto.prcVenda, pDto.quantidade, pDto.obs, pDto.ativo,
                pDto.ultimaAlteracao
        );
    }
}
