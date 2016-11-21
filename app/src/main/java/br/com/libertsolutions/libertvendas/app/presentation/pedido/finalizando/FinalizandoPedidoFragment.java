package br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizando;

import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.fragment.LibertVendasFragment;

/**
 * @author Filipe Bezerra
 */
public class FinalizandoPedidoFragment extends LibertVendasFragment {

    public static FinalizandoPedidoFragment newInstance() {
        return new FinalizandoPedidoFragment();
    }

    @Override protected int provideContentViewResource() {
        return R.layout.fragment_finaliza_pedido;
    }

}
