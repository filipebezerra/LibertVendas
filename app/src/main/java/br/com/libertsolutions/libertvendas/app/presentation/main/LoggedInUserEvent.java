package br.com.libertsolutions.libertvendas.app.presentation.main;

import br.com.libertsolutions.libertvendas.app.domain.pojo.LoggedUser;

/**
 * @author Filipe Bezerra
 */
public class LoggedInUserEvent {

    private final LoggedUser mUser;

    private LoggedInUserEvent(final LoggedUser user) {
        mUser = user;
    }

    static LoggedInUserEvent logged(final LoggedUser user) {
        return new LoggedInUserEvent(user);
    }

    public LoggedUser getUser() {
        return mUser;
    }
}
