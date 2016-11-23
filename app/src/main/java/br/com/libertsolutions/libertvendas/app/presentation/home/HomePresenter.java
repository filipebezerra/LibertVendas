package br.com.libertsolutions.libertvendas.app.presentation.home;

import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Empresa;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import br.com.libertsolutions.libertvendas.app.presentation.base.BasePresenter;
import br.com.libertsolutions.libertvendas.app.presentation.events.UsuarioLogadoEvent;
import br.com.libertsolutions.libertvendas.app.presentation.util.ExtrasExtractor;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static org.greenrobot.eventbus.ThreadMode.MAIN;

/**
 * @author Filipe Bezerra
 */
class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    private final SettingsRepository mSettingsRepository;

    HomePresenter(SettingsRepository pSettingsRepository) {
        mSettingsRepository = pSettingsRepository;
    }

    @Subscribe(threadMode = MAIN, sticky = true) public void onUsuarioLogadoEvent(
            UsuarioLogadoEvent pEvent) {
        Vendedor vendedor = pEvent.getVendedor();
        Empresa empresa = pEvent.getEmpresa();
        getView().showUsuarioLogado(vendedor.getNome(), empresa.getNome());

        if (!mSettingsRepository.isFirstTimeFeaturedMenuShown()) {
            mSettingsRepository.setFirstTimeFeaturedMenuShown();
            getView().showFeaturedMenu();
        }
    }

    @Override
    public void clickNavigationMenuSettings() {
        getView().navigateToSettings();
    }

    @Override
    public void clickNavigationMenuClientes() {
        getView().navigateToClientes();
    }

    @Override
    public void clickNavigationMenuProdutos() {
        getView().navigateToProdutos();
    }

    @Override
    public void clickNavigationMenuPedidos() {
        getView().navigateToPedidos();
    }

    @Override public void getClienteFromResult(ExtrasExtractor<Cliente> pClienteExtrasExtractor) {
        final Cliente cliente = pClienteExtrasExtractor.extractExtra();
        if (cliente != null) {
            EventBus.getDefault().postSticky(NewClienteCadastradoEvent.notifyEvent(cliente));
        }
    }

    @Override public void getPedidoFromResult(ExtrasExtractor<Pedido> pPedidoExtrasExtractor) {
        final Pedido pedido = pPedidoExtrasExtractor.extractExtra();
        if (pedido != null) {
            EventBus.getDefault().postSticky(NewPedidoCadastradoEvent.notifyEvent(pedido));
        }
    }

}
