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

        void updateChangedItemAtPosition(int pPosition);

        void updateInsertedItemAtPosition(int pPosition);

        void navigateToCliente(Cliente pCliente);
    }

    interface Presenter extends MvpPresenter<ListaClientesContract.View> {

        void loadListaClientes();

        void handleSingleTapUp(int pPosition);

        void handleResultClienteEditado(Cliente pClienteEditado);
    }
}
