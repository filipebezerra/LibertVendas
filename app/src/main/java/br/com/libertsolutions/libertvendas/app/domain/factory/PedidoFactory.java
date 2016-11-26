package br.com.libertsolutions.libertvendas.app.domain.factory;

import br.com.libertsolutions.libertvendas.app.data.util.ApiUtils;
import br.com.libertsolutions.libertvendas.app.domain.dto.PedidoDto;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class PedidoFactory {
    private PedidoFactory() {/* No instances */}

    public static List<PedidoDto> createDtoList(List<Pedido> pPedidoList) {
        List<PedidoDto> pedidoDtoList = new ArrayList<>();
        for (Pedido pedido : pPedidoList) {
            pedidoDtoList.add(createDto(pedido));
        }
        return pedidoDtoList;
    }

    private static PedidoDto createDto(Pedido pPedido) {
        return new PedidoDto(
                pPedido.getTipo(),
                ApiUtils.formatApiDateTime(pPedido.getDataEmissao()),
                pPedido.getDesconto(),
                pPedido.getPercentualDesconto(),
                pPedido.getObservacao(),
                pPedido.getCliente().getIdCliente(),
                pPedido.getFormaPagamento().getIdFormaPagamento(),
                pPedido.getTabelaPreco().getIdTabela(),
                ItemPedidoFactory.createDtoList(pPedido.getItens())
        );
    }
}
