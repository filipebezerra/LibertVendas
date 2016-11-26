package br.com.libertsolutions.libertvendas.app.domain.factory;

import br.com.libertsolutions.libertvendas.app.domain.dto.ItemPedidoDto;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemPedido;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
class ItemPedidoFactory {
    private ItemPedidoFactory() {/* No instances */}

    static List<ItemPedidoDto> createDtoList(List<ItemPedido> pItemPedidoList) {
        List<ItemPedidoDto> itemPedidoDtoList = new ArrayList<>();
        for (ItemPedido item : pItemPedidoList) {
            itemPedidoDtoList.add(createDto(item));
        }
        return itemPedidoDtoList;
    }

    private static ItemPedidoDto createDto(ItemPedido pItemPedido) {
        final double subTotal = pItemPedido.getPrecoVenda() * pItemPedido.getQuantidade();

        return new ItemPedidoDto(
                pItemPedido.getId(), pItemPedido.getPrecoVenda(),
                subTotal, pItemPedido.getDesconto(), pItemPedido.getQuantidade(),
                pItemPedido.getObservacao(), pItemPedido.getProduto().getIdProduto()
        );
    }
}
