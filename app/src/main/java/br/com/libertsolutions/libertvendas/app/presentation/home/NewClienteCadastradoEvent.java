package br.com.libertsolutions.libertvendas.app.presentation.home;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;

/**
 * @author Filipe Bezerra
 */
public class NewClienteCadastradoEvent {
    private final Cliente mCliente;

    private NewClienteCadastradoEvent(Cliente pCliente) {
        mCliente = pCliente;
    }

    static NewClienteCadastradoEvent notifyEvent(Cliente pCliente) {
        return new NewClienteCadastradoEvent(pCliente);
    }

    public Cliente getCliente() {
        return mCliente;
    }
}
