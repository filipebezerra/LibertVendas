package br.com.libertsolutions.libertvendas.app.presentation.cadastrocliente;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.presentation.base.BaseEvent;

/**
 * @author Filipe Bezerra
 */
public class ClienteSavedEvent extends BaseEvent<Cliente> {

    private ClienteSavedEvent(Cliente pEventValue) {
        super(pEventValue);
    }

    static ClienteSavedEvent newEvent(Cliente pEventValue) {
        return new ClienteSavedEvent(pEventValue);
    }
}
