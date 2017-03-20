package br.com.libertsolutions.libertvendas.app.data.settings;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Company;
import br.com.libertsolutions.libertvendas.app.domain.pojo.LoggedUser;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Salesman;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Settings;
import rx.Observable;

/**
 * @author Filipe Bezerra
 */
public interface SettingsRepository {

    boolean isInitialFlowDone();

    void  setInitialFlowDone();

    boolean isAllSettingsPresent();

    boolean isUserLoggedIn();

    void setLoggedUser(Salesman user, Company defaultCompany);

    Observable<LoggedUser> getLoggedUser();

    Settings getSettings();
}
