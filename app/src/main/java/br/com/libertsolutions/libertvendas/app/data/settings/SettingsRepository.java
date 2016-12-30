package br.com.libertsolutions.libertvendas.app.data.settings;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Empresa;
import br.com.libertsolutions.libertvendas.app.domain.pojo.LoggedUser;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Settings;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;

/**
 * @author Filipe Bezerra
 */
public interface SettingsRepository {

    void setInitialConfigurationDone();

    boolean isInitialConfigurationDone();

    boolean hasRequiredSettings();

    Settings loadSettings();

    boolean isUserLoggedIn();

    void setLoggedInUser(Vendedor vendedor, Empresa empresa);

    LoggedUser getLoggedInUser();

    void setInitialDataImportationDone();

    boolean isInitialDataImportationDone();
}
