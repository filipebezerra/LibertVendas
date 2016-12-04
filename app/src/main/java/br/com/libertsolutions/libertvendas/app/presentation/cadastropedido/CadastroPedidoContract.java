package br.com.libertsolutions.libertvendas.app.presentation.cadastropedido;

import br.com.libertsolutions.libertvendas.app.presentation.base.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.base.MvpView;

/**
 * @author Filipe Bezerra
 */
interface CadastroPedidoContract {

    interface View extends MvpView {

        void navigateToStepListaClientes();

        void navigateToStepFinalizandoPedido();
    }

    interface Presenter extends MvpPresenter<View> {


    }
}
