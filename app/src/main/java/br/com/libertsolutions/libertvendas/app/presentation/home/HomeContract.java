package br.com.libertsolutions.libertvendas.app.presentation.home;

import br.com.libertsolutions.libertvendas.app.presentation.base.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.base.MvpView;

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

        void handleSettingsNavigationItemSelected();

        void handleClientesNavigationItemSelected();

        void handleProdutosNavigationItemSelected();

        void handlePedidosNavigationItemSelected();
    }
}
