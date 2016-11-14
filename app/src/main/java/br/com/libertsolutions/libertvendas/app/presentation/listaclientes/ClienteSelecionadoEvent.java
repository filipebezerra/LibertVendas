package br.com.libertsolutions.libertvendas.app.presentation.listaclientes;

import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;

/**
 * @author Filipe Bezerra
 */
public class ClienteSelecionadoEvent {
    private final Cliente mCliente;

    private ClienteSelecionadoEvent(Cliente pCliente) {
        mCliente = pCliente;
    }

    public static ClienteSelecionadoEvent newEvent(@NonNull Cliente pCliente) {
        return new ClienteSelecionadoEvent(pCliente);
    }

    public Cliente getCliente() {
        return mCliente;
    }
}
