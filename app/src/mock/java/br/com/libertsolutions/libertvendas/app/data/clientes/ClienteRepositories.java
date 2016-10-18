package br.com.libertsolutions.libertvendas.app.data.clientes;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * @author Filipe Bezerra
 */
public class ClienteRepositories {
    private static ClienteService sService = null;

    public synchronized static ClienteService getService(@NonNull Context context) {
        if (sService == null) {
            sService = new MockClienteServiceImpl();
        }
        return sService;
    }
}
