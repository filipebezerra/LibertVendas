package br.com.libertsolutions.libertvendas.app.presentation.importacao;

import br.com.libertsolutions.libertvendas.app.presentation.base.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.base.MvpView;

/**
 * @author Filipe Bezerra
 */
interface ImportacaoContract {

    interface View extends MvpView {
        void showLoading();

        void showSuccessMessage();

        void hideLoadingWithSuccess();

        void hideLoadingWithFail();

        void showMessageDialog(String pMessage);

        void showDeviceNotConnectedError();

        void showServerError();

        void showNetworkError();

        void showUnknownError();

        void invalidateMenu();

        void navigateToMainActivity();

        void finishActivity();

    }

    interface Presenter extends MvpPresenter<View> {

        void startSync(boolean pDeviceConnected);

        void handleClickDoneMenuItem();

        boolean isSyncDone();

        void handleCancelOnSyncError();

        void handleAnimationEnd(boolean pSuccess);

    }

}
