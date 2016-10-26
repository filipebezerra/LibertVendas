package br.com.libertsolutions.libertvendas.app.presentation.home;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.presentation.util.ExtrasExtractor;
import org.greenrobot.eventbus.EventBus;

/**
 * @author Filipe Bezerra
 */
class HomePresenter implements HomeContract.Presenter {
    private final HomeContract.View mView;

    HomePresenter(HomeContract.View view) {
        mView = view;
    }

    @Override
    public void clickNavigationMenuSettings() {
        mView.navigateToSettings();
    }

    @Override
    public void clickNavigationMenuClientes() {
        mView.navigateToClientes();
    }

    @Override
    public void clickNavigationMenuProdutos() {
        mView.navigateToProdutos();
    }

    @Override
    public void clickNavigationMenuPedidos() {
        mView.navigateToPedidos();
    }

    @Override
    public void getClienteFromResult(ExtrasExtractor<Cliente> pClienteExtrasExtractor) {
        final Cliente cliente = pClienteExtrasExtractor.extractExtra();
        if (cliente != null) {
            EventBus.getDefault().postSticky(NewClienteCadastradoEvent.notifyEvent(cliente));
        }
    }
}
