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

        void showDrawer();
    }

    interface Presenter extends MvpPresenter<View>, LifecyclePresenter {


    }
}
