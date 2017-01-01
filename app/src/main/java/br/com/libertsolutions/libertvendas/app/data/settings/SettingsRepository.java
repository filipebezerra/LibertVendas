package br.com.libertsolutions.libertvendas.app.data.settings;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Settings;

/**
 * @author Filipe Bezerra
 */
public interface SettingsRepository {

    void setInitialConfigurationDone();

    boolean isInitialConfigurationDone();

    boolean hasRequiredSettings();

    Settings loadSettings();

    boolean isUserLoggedIn();

    void setLoggedInUser(int userId);

    int getLoggedInUser();

    void setInitialDataImportationDone();

    boolean isInitialDataImportationDone();

    void setSincronizarPedidoAutomaticamente(boolean isEnabled);
}
