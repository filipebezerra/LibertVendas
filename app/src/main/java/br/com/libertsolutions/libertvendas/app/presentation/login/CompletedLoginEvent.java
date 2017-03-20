package br.com.libertsolutions.libertvendas.app.presentation.login;

import br.com.libertsolutions.libertvendas.app.domain.pojo.LoggedUser;

/**
 * @author Filipe Bezerra
 */
public class CompletedLoginEvent {

    private final LoggedUser mUser;

    private CompletedLoginEvent(final LoggedUser user) {
        mUser = user;
    }

    static CompletedLoginEvent newEvent(final LoggedUser user) {
        return new CompletedLoginEvent(user);
    }

    public LoggedUser getUser() {
        return mUser;
    }
}
