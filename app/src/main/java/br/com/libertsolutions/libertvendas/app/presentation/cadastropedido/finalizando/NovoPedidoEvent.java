package br.com.libertsolutions.libertvendas.app.presentation.cadastropedido.finalizando;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;

/**
 * @author Filipe Bezerra
 */
public class NovoPedidoEvent {

    private final Pedido mPedido;

    private NovoPedidoEvent(Pedido pedido) {
        mPedido = pedido;
    }

    static NovoPedidoEvent newEvent(Pedido novoPedido) {
        return new NovoPedidoEvent(novoPedido);
    }

    public Pedido getPedido() {
        return mPedido;
    }
}
