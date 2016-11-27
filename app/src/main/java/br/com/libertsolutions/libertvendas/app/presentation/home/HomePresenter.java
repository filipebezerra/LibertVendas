package br.com.libertsolutions.libertvendas.app.presentation.home;

import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Empresa;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import br.com.libertsolutions.libertvendas.app.presentation.base.BasePresenter;
import br.com.libertsolutions.libertvendas.app.presentation.login.UsuarioLogadoEvent;
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

    @Override public void handleSettingsNavigationItemSelected() {
        getView().navigateToSettings();
    }

    @Override public void handleClientesNavigationItemSelected() {
        getView().navigateToClientes();
    }

    @Override public void handleProdutosNavigationItemSelected() {
        getView().navigateToProdutos();
    }

    @Override public void handlePedidosNavigationItemSelected() {
        getView().navigateToPedidos();
    }
}
