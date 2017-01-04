package br.com.libertsolutions.libertvendas.app.presentation.cadastropedido;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemPedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpView;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
interface CadastroPedidoContract {

    interface View extends MvpView {

        void navigateToStepListaProdutos();

        void navigateToStepListaClientes();

        void navigateToStepFinalizandoPedido();

        void initializeSteps(List<ItemPedido> itens, Cliente cliente);
    }

    interface Presenter extends MvpPresenter<View> {

        void initializeView(Pedido pedidoEmEdicao);
    }
}
