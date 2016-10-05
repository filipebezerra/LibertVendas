package br.com.libertsolutions.libertvendas.app.presentation.listaclientes;

import java.util.List;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;

/**
 * @author Filipe Bezerra
 */

interface ListaClientesContract {
    interface View {
        void showListaClientes(List<Cliente> pClienteList);
    }

    interface Presenter {
        void loadListaClientes();
    }
}
