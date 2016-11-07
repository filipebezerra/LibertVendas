package br.com.libertsolutions.libertvendas.app.presentation.login;

/**
 * @author Filipe Bezerra
 */
interface LoginContract {
    interface View {
        LoginViewModel extractViewModel();

        void hideRequiredMessages();

        void displayRequiredMessageForFieldCpfCnpj();

        void displayRequiredMessageForFieldSenha();

        void displayValidationError(String pMessage);

        void showFeedbackMessage(String pMessage);

        void showServerError();

        void showNetworkError();

        void showUnknownError();

        void showIdle();

        void showLoading();

        void blockEditEntries();

        void unblockEditEntries();

        void showErrorIndicator();

        void showDeviceNotConnectedError();

        void showCompletedIndicator();

        void resultAsOk(int resultCode);

        void finishActivity();
    }

    interface Presenter {
        void initializeView();

        void clickButtonEntrar(boolean pDeviceConnected);

        void handleEditEntriesTextChanged();

        void stopWork();

        void finalizeView();
    }
}
