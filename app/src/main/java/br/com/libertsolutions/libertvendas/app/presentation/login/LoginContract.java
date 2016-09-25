package br.com.libertsolutions.libertvendas.app.presentation.login;

/**
 * @author Filipe Bezerra
 */

interface LoginContract {
    interface View {

        void resultAsOk(int resultCode);
    }

    interface Presenter {

        void clickButtonEntrar();
    }
}
