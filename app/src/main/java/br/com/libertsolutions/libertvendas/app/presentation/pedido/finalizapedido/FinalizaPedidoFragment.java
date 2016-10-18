package br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizapedido;

import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.fragment.LibertVendasFragment;

/**
 * @author Filipe Bezerra
 */
public class FinalizaPedidoFragment extends LibertVendasFragment {

    public static FinalizaPedidoFragment newInstance() {
        return new FinalizaPedidoFragment();
    }

    @Override
    protected int provideContentViewResource() {
        return R.layout.fragment_finaliza_pedido;
    }
}
