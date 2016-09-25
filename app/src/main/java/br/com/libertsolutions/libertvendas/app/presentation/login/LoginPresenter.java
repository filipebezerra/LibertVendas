package br.com.libertsolutions.libertvendas.app.presentation.login;

/**
 * @author Filipe Bezerra
 */

class LoginPresenter implements LoginContract.Presenter {
    private final LoginContract.View mView;

    LoginPresenter(LoginContract.View view) {
        mView = view;
    }
}
