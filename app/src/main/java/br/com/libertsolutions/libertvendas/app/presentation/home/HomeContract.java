package br.com.libertsolutions.libertvendas.app.presentation.home;

import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpView;

/**
 * @author Filipe Bezerra
 */
interface HomeContract {

    interface View extends MvpView {

        void setupViews(final String nomeVendedor, final String nomeEmpresa);

        void startInitialConfiguration();

        void startLogin();

        void startDataImportation();

        void finalizeView();
    }

    interface Presenter extends MvpPresenter<View> {

        void initializeView();

        void handleViewAfterResulted(int requestCode, int resultCode);
    }
}
