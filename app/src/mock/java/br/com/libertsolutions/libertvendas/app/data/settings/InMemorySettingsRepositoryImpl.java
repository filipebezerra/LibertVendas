package br.com.libertsolutions.libertvendas.app.data.settings;

import android.content.Context;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Settings;

/**
 * @author Filipe Bezerra
 */
public class InMemorySettingsRepositoryImpl implements SettingsRepository {
    private static boolean sIsFirstTimeSettingsLaunch = true;

    private static Settings sSettings = Settings.create("http://libertvendas.com/api/", true, true);

    public InMemorySettingsRepositoryImpl(Context context) {}

    @Override
    public boolean isFirstTimeSettingsLaunch() {
        return sIsFirstTimeSettingsLaunch;
    }

    @Override
    public void setFirstTimeSettingsLaunch() {
        sIsFirstTimeSettingsLaunch = false;
    }

    @Override
    public boolean hasAllSettingsFields() {
        return true;
    }

    @Override
    public Settings loadSettings() {
        return sSettings;
    }
}
