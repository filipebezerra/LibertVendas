package br.com.libertsolutions.libertvendas.app.presentation.pedido;

import br.com.libertsolutions.libertvendas.app.presentation.base.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.base.MvpView;

/**
 * @author Filipe Bezerra
 */
interface PedidoContract {

    interface View extends MvpView {
        void goToFinalizandoPedidoStep();
    }

    interface Presenter extends MvpPresenter<View> {

    }

}
