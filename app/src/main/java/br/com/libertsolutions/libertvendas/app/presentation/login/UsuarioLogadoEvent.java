package br.com.libertsolutions.libertvendas.app.presentation.login;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Empresa;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;

/**
 * @author Filipe Bezerra
 */
public class UsuarioLogadoEvent {
    private final Vendedor mVendedor;

    private final Empresa mEmpresa;

    private UsuarioLogadoEvent(Vendedor pVendedor, Empresa pEmpresa) {
        mVendedor = pVendedor;
        mEmpresa = pEmpresa;
    }

    public static UsuarioLogadoEvent newEvent(Vendedor pVendedor, Empresa pEmpresa) {
        return new UsuarioLogadoEvent(pVendedor, pEmpresa);
    }

    public Vendedor getVendedor() {
        return mVendedor;
    }

    public Empresa getEmpresa() {
        return mEmpresa;
    }
}
