package br.com.libertsolutions.libertvendas.app.presentation.importacao;

import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpView;

/**
 * @author Filipe Bezerra
 */
interface ImportacaoContract {

    interface View extends MvpView {

        void showLoading();

        void showOfflineMessage();

        void hideLoadingWithSuccess();

        void showSuccessMessage();

        void showMenu();

        void hideLoadingWithFail();

        void showServerError();

        void showNetworkError();

        void showUnknownError();

        void finalizeView();

        void finalizeViewWithSuccess();
    }

    interface Presenter extends MvpPresenter<View> {

        boolean handleMenuVisibility();

        void handleActionDone();

        void startSync();

        void handleAnimationEnd(boolean success);

        void cancel();
    }
}
