package br.com.libertsolutions.libertvendas.app.presentation.settings;

import android.support.v4.util.PatternsCompat;
import br.com.libertsolutions.libertvendas.app.PresentationInjection;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.data.sync.SyncTaskService;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Settings;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.BasePresenter;
import br.com.libertsolutions.libertvendas.app.presentation.utils.NumberUtils;
import java.util.regex.Pattern;

/**
 * @author Filipe Bezerra
 */
class SettingsPresenter extends BasePresenter<SettingsContract.View>
        implements SettingsContract.Presenter {

    private final SettingsRepository mSettingsRepository;

    private int mLastKnownSyncPeriod;

    SettingsPresenter(final SettingsRepository settingsRepository) {
        mSettingsRepository = settingsRepository;
        mLastKnownSyncPeriod = mSettingsRepository.getSyncPeriod();
    }

    @Override public boolean handleActionDoneVisibility() {
        return !mSettingsRepository.isInitialConfigurationDone();
    }

    @Override public void handleActionDone() {
        if (!mSettingsRepository.hasRequiredSettings()) {
            getView().showSettingsRequiredMessage();
            return;
        }

        final Settings settings = mSettingsRepository.loadSettings();

        if (!PatternsCompat.WEB_URL.matcher(settings.getServerAddress()).matches()) {
            getView().showInvalidServerAddressMessage();
            return;
        }

        if (Pattern.compile("api").matcher(settings.getServerAddress()).find()) {
            getView().showInvalidServerAddressWithPathApiMessage();
            return;
        }

        if (Pattern.compile("\\s").matcher(settings.getAuthenticationKey()).find()) {
            getView().showInvalidAuthenticationKeyMessage();
            return;
        }

        mSettingsRepository.setInitialConfigurationDone();
        getView().navigateToHome();
    }

    @Override public void handleSyncPeriodPreferenceChanged(final String newPeriodValue) {
        if (NumberUtils.isNumber(newPeriodValue)) {
            final int newPeriod = NumberUtils.toInt(newPeriodValue);

            if (newPeriod != mLastKnownSyncPeriod) {
                SyncTaskService.cancelAll(PresentationInjection.provideContext());
                SyncTaskService.schedule(PresentationInjection.provideContext());
            }
        }
    }
}
