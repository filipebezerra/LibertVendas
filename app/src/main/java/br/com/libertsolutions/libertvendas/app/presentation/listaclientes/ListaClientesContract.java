package br.com.libertsolutions.libertvendas.app.presentation.listaclientes;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpView;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
interface ListaClientesContract {

    interface View extends MvpView {

        void showLoading();

        void hideLoading();

        void showClientes(List<Cliente> clientes);

        void navigateToCadastroCliente(Cliente cliente);

        void updateChangedItemAtPosition(int position);

        void updateInsertedItemAtPosition(int position);
    }

    interface Presenter extends MvpPresenter<View> {

        void loadClientes();

        void handleItemSelected(int position);

        void handleResultClienteEditado(Cliente clienteEditado);

        void refreshCustomerList();
    }
}
