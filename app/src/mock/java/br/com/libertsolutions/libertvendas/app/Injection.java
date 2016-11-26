package br.com.libertsolutions.libertvendas.app;

import android.content.Context;
import android.support.annotation.NonNull;

import br.com.libertsolutions.libertvendas.app.data.clientes.ClienteRepositories;
import br.com.libertsolutions.libertvendas.app.data.clientes.ClienteService;
import br.com.libertsolutions.libertvendas.app.data.formaspagamento.FormaPagamentoRepositories;
import br.com.libertsolutions.libertvendas.app.data.formaspagamento.FormaPagamentoService;
import br.com.libertsolutions.libertvendas.app.data.pedidos.PedidoService;
import br.com.libertsolutions.libertvendas.app.data.produtos.ProdutoService;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;

/**
 * @author Filipe Bezerra
 */
public class Injection {
    public static SettingsRepository provideSettingsRepository(@NonNull Context pContext) {
        return SettingsRepositories.getRepository(pContext);
    }

    public static ClienteService provideClienteService(@NonNull Context pContext) {
        return ClienteRepositories.getService(pContext);
    }

    public static ProdutoService provideProdutoService(@NonNull Context pContext) {
        return ProdutoRepositories.getService(pContext);
    }

    public static PedidoService providePedidoService(@NonNull Context pContext) {
        return PedidoRepositories.getService(pContext);
    }

    public static FormaPagamentoService provideFormaPagamentoService(@NonNull Context pContext) {
        return FormaPagamentoRepositories.getService(pContext);
    }
}
