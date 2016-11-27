package br.com.libertsolutions.libertvendas.app.presentation.cadastrocliente;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.presentation.base.BaseEvent;

/**
 * @author Filipe Bezerra
 */
public class NovoClienteEvent extends BaseEvent<Cliente> {

    private NovoClienteEvent(Cliente pEventValue) {
        super(pEventValue);
    }

    static NovoClienteEvent newEvent(Cliente pEventValue) {
        return new NovoClienteEvent(pEventValue);
    }
}
