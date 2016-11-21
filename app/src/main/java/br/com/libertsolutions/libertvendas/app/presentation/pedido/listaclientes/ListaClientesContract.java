package br.com.libertsolutions.libertvendas.app.presentation.pedido.listaclientes;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.presentation.base.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.base.MvpView;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
interface ListaClientesContract {

    interface View extends MvpView {
        void showLoading();

        void hideLoading();

        void showListaClientes(List<Cliente> pClienteList);
    }

    interface Presenter extends MvpPresenter<ListaClientesContract.View> {
        void loadListaClientes();

        void handleSingleTapUp(int pPosition);
    }

}
