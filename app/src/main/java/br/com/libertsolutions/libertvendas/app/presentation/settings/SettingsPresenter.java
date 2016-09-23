package br.com.libertsolutions.libertvendas.app.presentation.settings;

import br.com.libertsolutions.libertvendas.app.presentation.util.Navigator;

/**
 * @author Filipe Bezerra
 */

class SettingsPresenter implements SettingsContract.Presenter {
    private final SettingsContract.View mView;

    SettingsPresenter(SettingsContract.View view) {
        mView = view;
    }

    @Override
    public void handleClickHomeMenuItem() {
        mView.resultAsCancelled(Navigator.RESULT_CANCELED);
    }

    @Override
    public boolean canEnableOptionsMenu() {
        return true;
    }

    @Override
    public void handleClickDoneMenuItem() {
        mView.resultAsOk(Navigator.RESULT_OK);
    }
}
