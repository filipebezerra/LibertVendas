package br.com.libertsolutions.libertvendas.app;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.data.cidades.CidadeRepositories;
import br.com.libertsolutions.libertvendas.app.data.cidades.CidadeService;
import br.com.libertsolutions.libertvendas.app.data.clientes.ClienteRepositories;
import br.com.libertsolutions.libertvendas.app.data.clientes.ClienteService;
import br.com.libertsolutions.libertvendas.app.data.formaspagamento.FormaPagamentoRepositories;
import br.com.libertsolutions.libertvendas.app.data.formaspagamento.FormaPagamentoService;
import br.com.libertsolutions.libertvendas.app.data.pedidos.PedidoRepositories;
import br.com.libertsolutions.libertvendas.app.data.produtos.ProdutoRepositories;
import br.com.libertsolutions.libertvendas.app.data.produtos.ProdutoService;
import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepositories;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.data.vendedor.VendedorRepositories;
import br.com.libertsolutions.libertvendas.app.data.vendedor.VendedorService;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cidade;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Produto;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import br.com.libertsolutions.libertvendas.app.presentation.resources.ClienteResourcesRepository;
import br.com.libertsolutions.libertvendas.app.presentation.resources.CommonResourcesRepository;
import br.com.libertsolutions.libertvendas.app.presentation.resources.ResourcesRepositories;

/**
 * @author Filipe Bezerra
 */
public class Injection {
    public static SettingsRepository provideSettingsRepository(@NonNull Context pContext) {
        return SettingsRepositories.getRepository(pContext);
    }

    public static FormaPagamentoService provideFormaPagamentoService(@NonNull Context pContext) {
        return FormaPagamentoRepositories.getService(pContext);
    }

    public static Repository<FormaPagamento> provideFormaPagamentoRepository(
            @NonNull Context pContext) {
        return FormaPagamentoRepositories.getRepository(pContext);
    }

    public static CidadeService provideCidadeService(@NonNull Context pContext) {
        return CidadeRepositories.getService(pContext);
    }

    public static Repository<Cidade> provideCidadeRepository(@NonNull Context pContext) {
        return CidadeRepositories.getRepository(pContext);
    }

    public static ProdutoService provideProdutoService(@NonNull Context pContext) {
        return ProdutoRepositories.getService(pContext);
    }

    public static Repository<Produto> provideProdutoRepository(@NonNull Context pContext) {
        return ProdutoRepositories.getRepository(pContext);
    }

    public static ClienteService provideClienteService(@NonNull Context pContext) {
        return ClienteRepositories.getService(pContext);
    }

    public static Repository<Cliente> provideClienteRepository(@NonNull Context pContext) {
        return ClienteRepositories.getRepository(pContext);
    }

    public static CommonResourcesRepository provideCommonResourcesRepository(
            @NonNull Context pContext) {
        return ResourcesRepositories.getCommonResources(pContext);
    }

    public static ClienteResourcesRepository provideClienteResourcesRepository(
            @NonNull Context context) {
        return ResourcesRepositories.getClienteResources(context);
    }

    public static Repository<Pedido> providePedidoRepository(@NonNull Context pContext) {
        return PedidoRepositories.getRepository(pContext);
    }

    public static VendedorService provideVendedorService(@NonNull Context pContext) {
        return VendedorRepositories.getService(pContext);
    }

    public static Repository<Vendedor> provideVendedorRepository(@NonNull Context pContext) {
        return VendedorRepositories.getRepository(pContext);
    }
}
