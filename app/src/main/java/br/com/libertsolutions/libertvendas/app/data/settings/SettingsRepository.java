package br.com.libertsolutions.libertvendas.app.data.settings;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Settings;

/**
 * @author Filipe Bezerra
 */
public interface SettingsRepository {
    boolean isFirstTimeSettingsLaunch();

    void setFirstTimeSettingsLaunch();

    boolean hasAllSettingsFields();

    Settings loadSettings();
}
