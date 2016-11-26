package br.com.libertsolutions.libertvendas.app.presentation.listaclientes;

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

        void updateListaClientes(int pPosition);

    }

    interface Presenter extends MvpPresenter<View> {

        void loadListaClientes();

        void handleSingleTapUp(int pPosition);

    }

}
