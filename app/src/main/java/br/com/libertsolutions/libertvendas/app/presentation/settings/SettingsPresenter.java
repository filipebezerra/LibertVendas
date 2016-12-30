package br.com.libertsolutions.libertvendas.app.presentation.settings;

import android.support.v4.util.PatternsCompat;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Settings;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.BasePresenter;
import java.util.regex.Pattern;

/**
 * @author Filipe Bezerra
 */
class SettingsPresenter extends BasePresenter<SettingsContract.View>
        implements SettingsContract.Presenter {

    private final SettingsRepository mSettingsRepository;

    SettingsPresenter(final SettingsRepository settingsRepository) {
        mSettingsRepository = settingsRepository;
    }

    @Override public boolean handleMenuVisibility() {
        return !mSettingsRepository.isInitialConfigurationDone();
    }

    @Override public void handleActionDone() {
        if (!mSettingsRepository.hasRequiredSettings()) {
            getView().showSettingsRequiredMessage();
            return;
        }

        final Settings settings = mSettingsRepository.loadSettings();

        if (!PatternsCompat.WEB_URL.matcher(settings.getUrlServidor()).matches()) {
            getView().showInvalidUrlServidorMessage();
            return;
        }

        if (Pattern.compile("api").matcher(settings.getUrlServidor()).find()) {
            getView().showInvalidUrlServidorComPathApiMessage();
            return;
        }

        if (Pattern.compile("\\s").matcher(settings.getChaveAutenticao()).find()) {
            getView().showInvalidChaveAutenticaoMessage();
            return;
        }

        mSettingsRepository.setInitialConfigurationDone();
        getView().finalizeViewWithSuccess();
    }
}
