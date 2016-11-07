package br.com.libertsolutions.libertvendas.app.presentation.events;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;

/**
 * @author Filipe Bezerra
 */
public class VendedorLogadoEvent {
    private final Vendedor mVendedor;

    private VendedorLogadoEvent(Vendedor pVendedor) {/* No constructor*/
        mVendedor = pVendedor;
    }

    public static VendedorLogadoEvent newEvent(Vendedor pVendedor) {
        return new VendedorLogadoEvent(pVendedor);
    }

    public Vendedor getVendedor() {
        return mVendedor;
    }
}
