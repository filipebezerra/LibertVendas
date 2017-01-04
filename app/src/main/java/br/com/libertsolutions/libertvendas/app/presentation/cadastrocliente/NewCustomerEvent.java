package br.com.libertsolutions.libertvendas.app.presentation.cadastrocliente;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;

/**
 * @author Filipe Bezerra
 */
public class NewCustomerEvent {

    private final Cliente mCliente;

    private NewCustomerEvent(Cliente cliente) {
        mCliente = cliente;
    }

    static NewCustomerEvent newEvent(Cliente cliente) {
        return new NewCustomerEvent(cliente);
    }

    public Cliente getCliente() {
        return mCliente;
    }
}
