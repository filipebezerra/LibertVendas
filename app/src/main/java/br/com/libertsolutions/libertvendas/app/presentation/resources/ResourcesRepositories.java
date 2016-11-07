package br.com.libertsolutions.libertvendas.app.presentation.resources;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * @author Filipe Bezerra
 */
public class ResourcesRepositories {
    private ResourcesRepositories() {/* No instances */}

    private static ClienteResourcesRepository sClienteResources = null;

    private static CommonResourcesRepository sCommonResourcesRepository = null;

    public synchronized static ClienteResourcesRepository getClienteResources(
            @NonNull Context context) {
        if (sClienteResources == null) {
            sClienteResources = new ClienteResourcesRepositoryImpl(context.getResources());
        }
        return sClienteResources;
    }

    public synchronized static CommonResourcesRepository getCommonResources(
            @NonNull Context pContext) {
        if (sCommonResourcesRepository == null) {
            sCommonResourcesRepository = new CommonResourcesRepositoryImpl(pContext.getResources());
        }
        return sCommonResourcesRepository;
    }
}
