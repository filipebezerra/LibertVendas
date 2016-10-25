package br.com.libertsolutions.libertvendas.app;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.data.clientes.ClienteRepositories;
import br.com.libertsolutions.libertvendas.app.data.formaspagamento.FormaPagamentoService;
import br.com.libertsolutions.libertvendas.app.data.pedidos.PedidoRepositories;
import br.com.libertsolutions.libertvendas.app.data.produtos.ProdutoService;
import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepositories;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.resources.ClienteResourcesRepository;
import br.com.libertsolutions.libertvendas.app.presentation.resources.ResourcesRepositories;

/**
 * @author Filipe Bezerra
 */
public class Injection {
    public static Repository<Cliente> provideClienteRepository(@NonNull Context pContext) {
        return ClienteRepositories.getRepository(pContext);
    }

    public static ClienteResourcesRepository provideClienteResourcesRepository(
            @NonNull Context context) {
        return ResourcesRepositories.getClienteResources(context);
    }

    public static Repository<Pedido> providePedidoRepository(@NonNull Context pContext) {
        return PedidoRepositories.getRepository(pContext);
    }

    public static ProdutoService provideProdutoService(@NonNull Context pContext) {
        return null;
    }

    public static FormaPagamentoService provideFormaPagamentoService(@NonNull Context pContext) {
        return null;
    }

    public static SettingsRepository provideSettingsRepository(@NonNull Context pContext) {
        return SettingsRepositories.getRepository(pContext);
    }
}
