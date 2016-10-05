package br.com.libertsolutions.libertvendas.app.presentation.home;

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
}
