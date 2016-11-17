package br.com.libertsolutions.libertvendas.app.presentation.home;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;

/**
 * @author Filipe Bezerra
 */
public class NewPedidoCadastradoEvent {
    private final Pedido mPedido;

    private NewPedidoCadastradoEvent(Pedido pPedido) {
        mPedido = pPedido;
    }

    static NewPedidoCadastradoEvent notifyEvent(Pedido pCliente) {
        return new NewPedidoCadastradoEvent(pCliente);
    }

    public Pedido getPedido() {
        return mPedido;
    }
}
