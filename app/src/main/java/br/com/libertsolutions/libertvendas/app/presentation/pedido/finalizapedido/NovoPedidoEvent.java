package br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizapedido;

import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;

/**
 * @author Filipe Bezerra
 */
public class NovoPedidoEvent {
    private final Pedido mPedido;

    private NovoPedidoEvent(Pedido pPedido) {
        mPedido = pPedido;
    }

    public static NovoPedidoEvent newEvent(@NonNull Pedido pPedido) {
        return new NovoPedidoEvent(pPedido);
    }

    public Pedido getPedido() {
        return mPedido;
    }
}
