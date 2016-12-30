package br.com.libertsolutions.libertvendas.app.data.formapagamento;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.data.utils.ServiceFactory;

/**
 * @author Filipe Bezerra
 */
public class FormaPagamentoRepositories {

    private FormaPagamentoRepositories() {/* No instances */}

    private static FormaPagamentoService sService = null;

    private static FormaPagamentoRepository sRepository = null;

    public static synchronized FormaPagamentoService getService(@NonNull Context context) {
        if (sService == null) {
            sService = ServiceFactory.createService(context, FormaPagamentoService.class);
        }
        return sService;
    }

    public static synchronized FormaPagamentoRepository getRepository() {
        if (sRepository == null) {
            sRepository = new FormaPagamentoRepositoryImpl(new FormaPagamentoMapper());
        }
        return sRepository;
    }
}
