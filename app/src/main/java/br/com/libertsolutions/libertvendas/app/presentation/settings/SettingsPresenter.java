package br.com.libertsolutions.libertvendas.app.presentation.settings;

import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.presentation.util.Navigator;

/**
 * @author Filipe Bezerra
 */

class SettingsPresenter implements SettingsContract.Presenter {
    private final SettingsContract.View mView;

    private final SettingsRepository mSettingsRepository;

    SettingsPresenter(
            SettingsContract.View view, SettingsRepository settingsRepository) {
        mView = view;
        mSettingsRepository = settingsRepository;
    }

    @Override
    public void initializeView() {
        if (!mSettingsRepository.isFirstTimeSettingsLaunch()) {
            mView.enableSettingTabelaPrecoPadrao();
        }
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
        mSettingsRepository.setFirstTimeSettingsLaunch();
        mView.resultAsOk(Navigator.RESULT_OK);
    }
}
