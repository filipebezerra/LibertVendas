package br.com.libertsolutions.libertvendas.app.data.pedidos;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * @author Filipe Bezerra
 */
public class PedidoRepositories {
    private static PedidoService sService = null;

    public synchronized static PedidoService getService(@NonNull Context context) {
        if (sService == null) {
            sService = new MockPedidoServiceImpl();
        }
        return sService;
    }
}
