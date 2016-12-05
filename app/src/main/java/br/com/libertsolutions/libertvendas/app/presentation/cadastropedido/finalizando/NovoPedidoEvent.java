package br.com.libertsolutions.libertvendas.app.presentation.cadastropedido.finalizando;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.base.BaseEvent;

/**
 * @author Filipe Bezerra
 */
public class NovoPedidoEvent extends BaseEvent<Pedido> {

    private NovoPedidoEvent(Pedido pEventValue) {
        super(pEventValue);
    }

    static NovoPedidoEvent newEvent(Pedido pEventValue) {
        return new NovoPedidoEvent(pEventValue);
    }
}
