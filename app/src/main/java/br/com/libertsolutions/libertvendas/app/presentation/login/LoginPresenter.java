package br.com.libertsolutions.libertvendas.app.presentation.login;

import br.com.libertsolutions.libertvendas.app.presentation.util.Navigator;

/**
 * @author Filipe Bezerra
 */

class LoginPresenter implements LoginContract.Presenter {
    private final LoginContract.View mView;

    LoginPresenter(LoginContract.View view) {
        mView = view;
    }

    @Override
    public void clickButtonEntrar() {
        mView.resultAsOk(Navigator.RESULT_OK);
    }
}
