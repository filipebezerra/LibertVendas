package br.com.libertsolutions.libertvendas.app.presentation.login;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Empresa;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpView;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
interface LoginContract {

    interface View extends MvpView {

        void hideRequiredMessages();

        void showIdle();

        void showOfflineMessage();

        LoginInputValues getInputValues();

        void displayRequiredMessageForFieldSenha();

        void displayRequiredMessageForFieldCpfCnpj();

        void showFillRequiredFieldsMessage();

        void blockInputFields();

        void showLoading();

        void showSelectCompany(List<Empresa> empresas);

        void displayErrorIndicator();

        void displayVendedorSemEmpresasError();

        void unblockInputFields();

        void showCompletedIndicator();

        void showServerError();

        void showNetworkError();

        void showUnknownError();

        void showValidationError(String message);

        void finalizeView();

        void finalizeViewWithSuccess();
    }

    interface Presenter extends MvpPresenter<View> {

        void startLogin();

        void handleCompanySelected(Empresa empresa);

        void cancel();
    }
}
