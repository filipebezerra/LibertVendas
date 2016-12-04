package br.com.libertsolutions.libertvendas.app.data.settings;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Settings;

/**
 * @author Filipe Bezerra
 */
public interface SettingsRepository {

    boolean isInitialDataImportationFlowDone();

    void doneInitialDataImportationFlow();

    boolean isRequiredSettingsFieldsSet();

    Settings getSettings();

    boolean isUserLoggedIn();

    int getLoggedInUser();

    int getLoggedInUserCompany();

    void setLoggedInUser(int idVendedor, int idEmpresa);
    
    boolean isUserLearnedDrawer();

    void doneUserLearnedDrawer();
}
