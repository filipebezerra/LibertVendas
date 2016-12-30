package br.com.libertsolutions.libertvendas.app.domain.factory;

import br.com.libertsolutions.libertvendas.app.domain.dto.ItemTabelaDto;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemTabela;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Produto;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
class ItemTabelaFactory {

    private ItemTabelaFactory() {/* No instances */}

    static List<ItemTabela> createListItemTabela(List<ItemTabelaDto> pDtos) {
        List<ItemTabela> itemTabelaList = new ArrayList<>();

        for (ItemTabelaDto dto : pDtos) {
            itemTabelaList.add(createItemTabela(dto));
        }
        return itemTabelaList;
    }

    private static ItemTabela createItemTabela(ItemTabelaDto pDto) {
        Produto produto = ProdutoFactories.createProduto(pDto.produto);

        return ItemTabela.create(
                pDto.idItensTabela, pDto.prcVenda, pDto.idProduto, pDto.ultimaAlteracao, produto
        );
    }
}
