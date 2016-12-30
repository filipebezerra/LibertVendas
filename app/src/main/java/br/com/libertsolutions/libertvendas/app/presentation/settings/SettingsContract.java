package br.com.libertsolutions.libertvendas.app.presentation.settings;

import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpView;

/**
 * @author Filipe Bezerra
 */
interface SettingsContract {

    interface View extends MvpView {

        void showSettingsRequiredMessage();

        void showInvalidUrlServidorMessage();

        void showInvalidUrlServidorComPathApiMessage();

        void showInvalidChaveAutenticaoMessage();

        void finalizeViewWithSuccess();
    }

    interface Presenter extends MvpPresenter<View> {

        boolean handleMenuVisibility();

        void handleActionDone();
    }
}
