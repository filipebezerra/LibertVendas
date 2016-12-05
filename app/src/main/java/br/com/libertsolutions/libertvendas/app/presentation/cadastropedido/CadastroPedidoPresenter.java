package br.com.libertsolutions.libertvendas.app.presentation.cadastropedido;

import br.com.libertsolutions.libertvendas.app.presentation.base.BasePresenter;
import br.com.libertsolutions.libertvendas.app.presentation.listaclientes.ClienteSelecionadoEvent;
import br.com.libertsolutions.libertvendas.app.presentation.listaprodutos.ProdutosSelecionadosEvent;
import org.greenrobot.eventbus.Subscribe;

/**
 * @author Filipe Bezerra
 */
class CadastroPedidoPresenter extends BasePresenter<CadastroPedidoContract.View>
        implements CadastroPedidoContract.Presenter {

    @Subscribe(sticky = true, priority = 1) public void onProdutosSelecionadosEvent(
            ProdutosSelecionadosEvent pEvent) {
        getView().navigateToStepListaClientes();
    }

    @Subscribe(sticky = true, priority = 1) public void onClienteSelecionadoEvent(
            ClienteSelecionadoEvent pEvent) {
        getView().navigateToStepFinalizandoPedido();
    }
}
