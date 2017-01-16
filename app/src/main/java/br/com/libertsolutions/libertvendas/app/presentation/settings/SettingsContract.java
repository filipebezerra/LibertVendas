package br.com.libertsolutions.libertvendas.app.presentation.settings;

import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpView;

/**
 * @author Filipe Bezerra
 */
interface SettingsContract {

    interface View extends MvpView {

        void showSettingsRequiredMessage();

        void showInvalidServerAddressMessage();

        void showInvalidServerAddressWithPathApiMessage();

        void showInvalidAuthenticationKeyMessage();

        void navigateToHome();
    }

    interface Presenter extends MvpPresenter<View> {

        boolean handleActionDoneVisibility();

        void handleActionDone();

        void handleSyncPeriodPreferenceChanged(String newPeriodValue);
    }
}
