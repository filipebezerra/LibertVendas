package br.com.libertsolutions.libertvendas.app.presentation.home;

import br.com.libertsolutions.libertvendas.app.presentation.base.LifecyclePresenter;
import br.com.libertsolutions.libertvendas.app.presentation.base.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.base.MvpView;

/**
 * @author Filipe Bezerra
 */
interface HomeContract {

    interface View extends MvpView {

        void navigateToInitialDataImportationFlow();

        void showUsuarioLogado(String pNomeVendedor, String pNomeEmpresa);

        void showDrawer();

        boolean isviewingListaPedidos();

        void navigateToListaPedidos();

        boolean isViewingListaClientes();

        void navigateToListaClientes();

        boolean isViewingListaProdutos();

        void navigateToListaProdutos();

        void navigateToSettings();
    }

    interface Presenter extends MvpPresenter<View>, LifecyclePresenter {

        void handlePedidosNavigationItemSelected();

        void handleClientesNavigationItemSelected();

        void handleProdutosNavigationItemSelected();

        void handleSettingsNavigationItemSelected();
    }
}
