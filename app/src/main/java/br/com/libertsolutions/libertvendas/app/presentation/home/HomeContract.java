package br.com.libertsolutions.libertvendas.app.presentation.home;

/**
 * @author Filipe Bezerra
 */
interface HomeContract {
    interface View {
        void navigateToSettings();

        void navigateToClientes();
    }

    interface Presenter {
        void clickNavigationMenuSettings();

        void clickNavigationMenuClientes();
    }
}
