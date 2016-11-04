package br.com.libertsolutions.libertvendas.app.presentation.importacao;

/**
 * @author Filipe Bezerra
 */
interface ImportacaoContract {
    interface View {
        void showLoading();

        void showSuccessMessage();

        void hideLoadingWithSuccess();

        void hideLoadingWithFail();

        void showErrorMessage(Throwable error);

        void showDeviceNotConnectedError();

        void showServerError();

        void showNetworkError();

        void showUnknownError();

        void showUnavailableServerError();

        void invalidateMenu();

        void navigateToMainActivity();

        void finishActivity();

    }

    interface Presenter {
        void startSync(boolean deviceConnected);

        void handleClickDoneMenuItem();

        boolean isSyncDone();

        void handleCancelOnSyncError();

        void handleAnimationEnd(boolean success);
    }
}
