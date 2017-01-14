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

    static List<ItemTabela> toPojoList(List<ItemTabelaDto> itemTabelaDtos) {
        List<ItemTabela> itemTabelaList = new ArrayList<>();

        for (ItemTabelaDto dto : itemTabelaDtos) {
            itemTabelaList.add(toPojo(dto));
        }
        return itemTabelaList;
    }

    private static ItemTabela toPojo(ItemTabelaDto itemTabelaDto) {
        Produto produto = ProdutoFactories.createProduto(itemTabelaDto.produto);

        return ItemTabela.create(
                itemTabelaDto.idItensTabela, itemTabelaDto.prcVenda,
                itemTabelaDto.idProduto, itemTabelaDto.ultimaAlteracao, produto
        );
    }
}
