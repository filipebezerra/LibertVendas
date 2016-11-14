package br.com.libertsolutions.libertvendas.app.presentation.events;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;

/**
 * @author Filipe Bezerra
 */
public class UsuarioLogadoEvent {
    private final Vendedor mVendedor;

    private UsuarioLogadoEvent(Vendedor pVendedor) {/* No constructor*/
        mVendedor = pVendedor;
    }

    public static UsuarioLogadoEvent newEvent(Vendedor pVendedor) {
        return new UsuarioLogadoEvent(pVendedor);
    }

    public Vendedor getVendedor() {
        return mVendedor;
    }
}
