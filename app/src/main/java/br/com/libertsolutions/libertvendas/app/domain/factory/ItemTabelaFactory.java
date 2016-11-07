package br.com.libertsolutions.libertvendas.app.domain.factory;

import br.com.libertsolutions.libertvendas.app.domain.dto.ItemTabelaDto;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemTabela;
import br.com.libertsolutions.libertvendas.app.domain.util.Preconditions;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class ItemTabelaFactory {
    private ItemTabelaFactory() {/* No instances */}

    public static List<ItemTabela> createListItemTabela(List<ItemTabelaDto> pDtos) {
        Preconditions.checkNotNull(pDtos, "List<ItemTabelaDto> can't be null");

        List<ItemTabela> itemTabelaList = new ArrayList<>();

        for (ItemTabelaDto dto : pDtos) {
            itemTabelaList.add(createItemTabela(dto));
        }
        return itemTabelaList;
    }

    private static ItemTabela createItemTabela(ItemTabelaDto pDto) {
        Preconditions.checkNotNull(pDto,
                "ItemTabelaDto can't be null");
        Preconditions.checkState(pDto.idItemTabela != 0,
                "ItemTabelaDto.idItemTabela can't be 0");
        Preconditions.checkState(pDto.idProduto != 0,
                "ItemTabelaDto.idProduto can't be empty");

        return new ItemTabela(
                pDto.idItemTabela, pDto.prcVenda, pDto.idProduto, pDto.ultimaAlteracao

        );
    }
}
