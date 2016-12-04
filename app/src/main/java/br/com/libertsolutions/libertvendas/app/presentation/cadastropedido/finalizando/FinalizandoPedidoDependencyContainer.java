package br.com.libertsolutions.libertvendas.app.presentation.cadastropedido.finalizando;

import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.Injection;
import br.com.libertsolutions.libertvendas.app.data.formaspagamento.FormaPagamentoRepository;
import br.com.libertsolutions.libertvendas.app.data.pedidos.PedidoRepository;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.presentation.resources.FinalizandoPedidoResourcesRepository;

/**
 * @author Filipe Bezerra
 */
class FinalizandoPedidoDependencyContainer {

    private final FormaPagamentoRepository mFormaPagamentoRepository;

    private final PedidoRepository mPedidoRepository;

    private final FinalizandoPedidoResourcesRepository mResourcesRepository;

    private final SettingsRepository mSettingsRepository;

    private FinalizandoPedidoDependencyContainer(@NonNull FinalizandoPedidoFragment pFragment) {
        mFormaPagamentoRepository = Injection.provideFormaPagamentoRepository();
        mPedidoRepository = Injection.providePedidoRepository();
        mResourcesRepository = Injection.provideFinalizandoPedidoResourcesRepository(pFragment.getContext());
        mSettingsRepository = Injection.provideSettingsRepository(pFragment.getContext());
    }

    static FinalizandoPedidoDependencyContainer createDependencyContainer(
            @NonNull FinalizandoPedidoFragment pFragment) {
        return new FinalizandoPedidoDependencyContainer(pFragment);
    }

    FormaPagamentoRepository getFormaPagamentoRepository() {
        return mFormaPagamentoRepository;
    }

    PedidoRepository getPedidoRepository() {
        return mPedidoRepository;
    }

    FinalizandoPedidoResourcesRepository getResourcesRepository() {
        return mResourcesRepository;
    }

    SettingsRepository getSettingsRepository() {
        return mSettingsRepository;
    }
}
