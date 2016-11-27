package br.com.libertsolutions.libertvendas.app.presentation.resources;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * @author Filipe Bezerra
 */
public class ResourcesRepositories {
    private ResourcesRepositories() {/* No instances */}

    private static CommonResourcesRepository sCommonResourcesRepository = null;

    private static CadastroClienteResourcesRepository sClienteResources = null;

    private static SelecioneProdutosResourcesRepository sSelecioneProdutosResourcesRepository = null;

    private static ImportacaoResourcesRepository sImportacaoResourcesRepository = null;

    private static FinalizaPedidoResourcesRepository sFinalizaPedidoResourcesRepository = null;

    public synchronized static CadastroClienteResourcesRepository getClienteResources(
            @NonNull Context context) {
        if (sClienteResources == null) {
            sClienteResources = new CadastroClienteResourcesRepositoryImpl(context.getResources());
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

    public synchronized static SelecioneProdutosResourcesRepository getSelecioneProdutosResourcesRepository(
            @NonNull Context pContext) {
        if (sSelecioneProdutosResourcesRepository == null) {
            sSelecioneProdutosResourcesRepository
                    = new SelecioneProdutosResourcesRepositoryImpl(pContext.getResources());
        }
        return sSelecioneProdutosResourcesRepository;
    }

    public synchronized static ImportacaoResourcesRepository getImportacaoResourcesRepository(
            @NonNull Context pContext) {
        if (sImportacaoResourcesRepository == null) {
            sImportacaoResourcesRepository =
                    new ImportacaoResourcesRepositoryImpl(pContext.getResources());
        }
        return sImportacaoResourcesRepository;
    }

    public synchronized static FinalizaPedidoResourcesRepository getFinalizaPedidoResourcesRepository(
            @NonNull Context pContext) {
        if (sFinalizaPedidoResourcesRepository == null) {
            sFinalizaPedidoResourcesRepository =
                    new FinalizaPedidoResourcesRepositoryImpl(pContext.getResources());
        }
        return sFinalizaPedidoResourcesRepository;
    }
}
