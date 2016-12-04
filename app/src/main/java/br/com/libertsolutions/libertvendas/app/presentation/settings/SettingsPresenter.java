package br.com.libertsolutions.libertvendas.app.presentation.settings;

import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.presentation.activity.Navigator;

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

        if (!mSettingsRepository.isInitialDataImportationFlowDone() &&
                mSettingsRepository.isRequiredSettingsFieldsSet()) {
            mView.resultAsOk(Navigator.RESULT_OK);
        }
    }

    @Override public void handleClickHomeMenuItem() {
        mView.resultAsCancelled(Navigator.RESULT_CANCELED);
    }

    @Override public boolean canEnableOptionsMenu() {
        return true;
    }

    @Override public void handleClickDoneMenuItem() {
        if (!mSettingsRepository.isRequiredSettingsFieldsSet()) {
            mView.showRequiredMessage();
            return;
        }

        mView.resultAsOk(Navigator.RESULT_OK);
    }
}
