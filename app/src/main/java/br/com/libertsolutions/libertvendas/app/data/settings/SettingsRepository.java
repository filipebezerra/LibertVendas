package br.com.libertsolutions.libertvendas.app.data.settings;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Settings;

/**
 * @author Filipe Bezerra
 */
public interface SettingsRepository {

    boolean isInitialDataImportationFlowDone();

    void doneInitialDataImportationFlow();

    boolean isRequiredSettingsFieldsSet();

    //region deprecated
    boolean isFirstTimeFeaturedMenuShown();

    void setFirstTimeFeaturedMenuShown();

    Settings loadSettings();

    boolean hasUsuarioLogado();

    void setUsuarioLogado(int idVendedor);

    int getUsuarioLogado();

    void setEmpresaLogada(int idEmpresa);

    int getEmpresaLogada();
    //endregion
}
