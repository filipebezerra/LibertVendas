package br.com.libertsolutions.libertvendas.app.domain.factory;

import br.com.libertsolutions.libertvendas.app.data.utils.ApiUtils;
import br.com.libertsolutions.libertvendas.app.domain.dto.ItemPedidoDto;
import br.com.libertsolutions.libertvendas.app.domain.dto.PedidoDto;
import br.com.libertsolutions.libertvendas.app.domain.entity.ItemPedidoEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.PedidoEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class PedidoFactory {

    private PedidoFactory() {/* No instances */}

    public static PedidoDto createDto(final PedidoEntity order) {
        PedidoDto pedidoDto = new PedidoDto();
        pedidoDto.appKey = order.getId();
        pedidoDto.tipo = order.getTipo();
        pedidoDto.dtEmissao = ApiUtils.formatApiDateTime(order.getDataEmissao());
        pedidoDto.desconto = order.getDesconto();
        pedidoDto.percDesc = order.getPercentualDesconto();
        pedidoDto.observacao = order.getObservacao();
        pedidoDto.idCliente = order.getCliente().getIdCliente();
        pedidoDto.idFormPgto = order.getFormaPagamento().getId();
        pedidoDto.idTabela = order.getTabela().getId();
        pedidoDto.status = order.getStatus();
        pedidoDto.itens = createItemsDto(order.getItens());
        return pedidoDto;
    }

    private static List<ItemPedidoDto> createItemsDto(final List<ItemPedidoEntity> orderItems) {
        List<ItemPedidoDto> orderItemsDto = new ArrayList<>();

        for (ItemPedidoEntity orderItem : orderItems) {
            ItemPedidoDto orderItemDto = new ItemPedidoDto();
            orderItemDto.appKey = orderItem.getId();
            orderItemDto.prcVenda = orderItem.getPrecoVenda();
            orderItemDto.subTotal = orderItem.getSubTotal();
            orderItemDto.quantidade = orderItem.getQuantidade();
            orderItemDto.idProduto = orderItem.getProduto().getId();
            orderItemsDto.add(orderItemDto);
        }

        return orderItemsDto;
    }
}
