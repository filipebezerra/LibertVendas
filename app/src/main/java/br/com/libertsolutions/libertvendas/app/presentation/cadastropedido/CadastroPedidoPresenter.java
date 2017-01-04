package br.com.libertsolutions.libertvendas.app.presentation.cadastropedido;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.listaclientes.ClienteSelecionadoEvent;
import br.com.libertsolutions.libertvendas.app.presentation.listaprodutos.ProdutosSelecionadosEvent;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.BasePresenter;
import org.greenrobot.eventbus.Subscribe;

/**
 * @author Filipe Bezerra
 */
public class CadastroPedidoPresenter extends BasePresenter<CadastroPedidoContract.View>
        implements CadastroPedidoContract.Presenter {

    @Override public void initializeView(final Pedido pedidoEmEdicao) {
        if (pedidoEmEdicao == null) {
            getView().navigateToStepListaProdutos();
        } else {
            getView().initializeSteps(pedidoEmEdicao.getItens(), pedidoEmEdicao.getCliente());
        }
    }

    @Subscribe(sticky = true, priority = 1) public void onEvent(ProdutosSelecionadosEvent event) {
        getView().navigateToStepListaClientes();
    }

    @Subscribe(sticky = true, priority = 1) public void onEvent(ClienteSelecionadoEvent event) {
        getView().navigateToStepFinalizandoPedido();
    }
}
