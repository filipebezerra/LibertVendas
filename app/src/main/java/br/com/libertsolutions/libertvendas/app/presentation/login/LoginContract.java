package br.com.libertsolutions.libertvendas.app.presentation.login;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Empresa;
import br.com.libertsolutions.libertvendas.app.presentation.base.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.base.MvpView;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
interface LoginContract {

    interface View extends MvpView {

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

        void showChooseEmpresaParaLogar(List<Empresa> pEmpresas);

        void resultAsOk(int resultCode);

        void finishActivity();

    }

    interface Presenter extends MvpPresenter<View> {

        void clickButtonEntrar(boolean pDeviceConnected);

        void handleEditEntriesTextChanged();

        void clickChooseEmpresaParaLogar(Empresa pEmpresa);

        void finalizeView();

    }

}
