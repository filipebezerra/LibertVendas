package br.com.libertsolutions.libertvendas.app.presentation.resources;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * @author Filipe Bezerra
 */
public class ResourcesRepositories {

    private ResourcesRepositories() {/* No instances */}

    private static CadastroClienteResourcesRepository sCadastroClienteResources = null;

    private static FinalizandoPedidoResourcesRepository sFinalizandoPedidoResources = null;

    public synchronized static CadastroClienteResourcesRepository getCadastroClienteResources(
            @NonNull Context context) {
        if (sCadastroClienteResources == null) {
            sCadastroClienteResources
                    = new CadastroClienteResourcesRepositoryImpl(context.getResources());
        }
        return sCadastroClienteResources;
    }

    public synchronized static FinalizandoPedidoResourcesRepository getFinalizandoPedidoResources(
            @NonNull Context context) {
        if (sFinalizandoPedidoResources == null) {
            sFinalizandoPedidoResources
                    = new FinalizandoPedidoResourcesRepositoryImpl(context.getResources());
        }
        return sFinalizandoPedidoResources;
    }
}
