package br.com.libertsolutions.libertvendas.app.presentation.login;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;

/**
 * @author Filipe Bezerra
 */
public class LoggedUserEvent {

    private final Vendedor mVendedor;

    private LoggedUserEvent(Vendedor vendedor) {
        mVendedor = vendedor;
    }

    public static LoggedUserEvent newEvent(Vendedor vendedor) {
        return new LoggedUserEvent(vendedor);
    }

    public Vendedor getVendedor() {
        return mVendedor;
    }
}
