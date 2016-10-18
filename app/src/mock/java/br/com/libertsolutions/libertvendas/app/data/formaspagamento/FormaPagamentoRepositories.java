package br.com.libertsolutions.libertvendas.app.data.formaspagamento;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * @author Filipe Bezerra
 */
public class FormaPagamentoRepositories {
    private static FormaPagamentoService sService = null;

    public synchronized static FormaPagamentoService getService(@NonNull Context context) {
        if (sService == null) {
            sService = new MockFormaPagamentoServiceImpl();
        }
        return sService;
    }
}
