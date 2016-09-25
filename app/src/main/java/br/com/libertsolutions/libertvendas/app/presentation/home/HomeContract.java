package br.com.libertsolutions.libertvendas.app.presentation.home;

/**
 * @author Filipe Bezerra
 */
interface HomeContract {
    interface View {

        void navigateToSettings();
    }

    interface Presenter {

        void clickNavigationMenuSettings();
    }
}
