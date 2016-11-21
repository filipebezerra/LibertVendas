package br.com.libertsolutions.libertvendas.app.presentation.pedido;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.presentation.base.BasePresenter;
import br.com.libertsolutions.libertvendas.app.presentation.listaclientes.ClienteSelecionadoEvent;
import org.greenrobot.eventbus.Subscribe;

/**
 * @author Filipe Bezerra
 */
class PedidoPresenter extends BasePresenter<PedidoContract.View>
        implements PedidoContract.Presenter {

    private Cliente mClienteSelecionado;

    @Override public void attachView(PedidoContract.View pView) {
        super.attachView(pView);
        registerForEvents();
    }

    @Subscribe public void onClienteSelecionadoEvent(ClienteSelecionadoEvent pEvent) {
        Cliente cliente = pEvent.getCliente();
        if (cliente != null) {
            mClienteSelecionado = cliente;
            getView().goToFinalizandoPedidoStep();
        }
    }

}
