package br.com.libertsolutions.libertvendas.app.presentation.home;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.base.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.base.MvpView;
import br.com.libertsolutions.libertvendas.app.presentation.util.ExtrasExtractor;

/**
 * @author Filipe Bezerra
 */
interface HomeContract {

    interface View extends MvpView {

        void showUsuarioLogado(String pNomeVendedor, String pNomeEmpresa);

        void showFeaturedMenu();

        void navigateToSettings();

        void navigateToClientes();

        void navigateToProdutos();

        void navigateToPedidos();
    }

    interface Presenter extends MvpPresenter<View> {

        void clickNavigationMenuSettings();

        void clickNavigationMenuClientes();

        void clickNavigationMenuProdutos();

        void clickNavigationMenuPedidos();

        void getClienteFromResult(ExtrasExtractor<Cliente> pClienteExtrasExtractor);

        void getPedidoFromResult(ExtrasExtractor<Pedido> pPedidoExtrasExtractor);

    }

}
