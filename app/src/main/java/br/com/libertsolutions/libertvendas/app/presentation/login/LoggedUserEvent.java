package br.com.libertsolutions.libertvendas.app.presentation.login;

import br.com.libertsolutions.libertvendas.app.domain.pojo.LoggedUser;

/**
 * @author Filipe Bezerra
 */
public class LoggedUserEvent {
    private final LoggedUser mLoggedUser;

    private LoggedUserEvent(LoggedUser pVendedor) {
        mLoggedUser = pVendedor;
    }

    public static LoggedUserEvent newEvent(LoggedUser pVendedor) {
        return new LoggedUserEvent(pVendedor);
    }

    public LoggedUser getLoggedUser() {
        return mLoggedUser;
    }
}
