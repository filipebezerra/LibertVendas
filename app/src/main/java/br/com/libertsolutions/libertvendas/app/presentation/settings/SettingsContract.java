package br.com.libertsolutions.libertvendas.app.presentation.settings;

/**
 * @author Filipe Bezerra
 */

interface SettingsContract {
    interface View {
        void resultAsCancelled(int resultCode);

        void resultAsOk(int resultCode);

        void enableSettingTabelaPrecoPadrao();
    }

    interface Presenter {
        void initializeView();

        void handleClickHomeMenuItem();

        boolean canEnableOptionsMenu();

        void handleClickDoneMenuItem();
    }
}
