package br.com.libertsolutions.libertvendas.app.data.settings;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Settings;

/**
 * @author Filipe Bezerra
 */
public interface SettingsRepository {

    boolean isInitialConfigurationDone();

    void setInitialConfigurationDone();

    boolean hasRequiredSettings();

    Settings loadSettings();

    boolean isUserLoggedIn();

    void setLoggedInUser(int userId);

    int getLoggedInUser();

    boolean isInitialDataImportationDone();

    void setInitialDataImportationDone();

    void setAutoSync(boolean isEnabled);
}
