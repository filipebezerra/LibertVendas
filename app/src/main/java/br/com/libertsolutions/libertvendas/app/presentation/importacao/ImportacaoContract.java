package br.com.libertsolutions.libertvendas.app.presentation.importacao;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;

/**
 * @author Filipe Bezerra
 */
interface ImportacaoContract {
    interface View {
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

    interface Presenter {
        void handleUsuarioLogadoEvent(Vendedor pVendedor);

        void startSync(boolean pDeviceConnected);

        void handleClickDoneMenuItem();

        boolean isSyncDone();

        void handleCancelOnSyncError();

        void handleAnimationEnd(boolean pSuccess);
    }
}
