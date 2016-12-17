package br.com.libertsolutions.libertvendas.app.presentation.settings;

/**
 * @author Filipe Bezerra
 */
interface SettingsContract {

    interface View {

        void resultAsCancelled(int resultCode);

        void resultAsOk(int resultCode);

        void showRequiredMessage();
    }

    interface Presenter {

        void handleClickHomeMenuItem();

        boolean canEnableOptionsMenu();

        void handleClickDoneMenuItem();
    }
}
