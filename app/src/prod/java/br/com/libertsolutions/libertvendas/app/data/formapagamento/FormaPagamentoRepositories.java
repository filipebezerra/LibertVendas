package br.com.libertsolutions.libertvendas.app.data.formapagamento;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.NetworkInjection;
import br.com.libertsolutions.libertvendas.app.data.repository.RealmMapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.FormaPagamentoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;

/**
 * @author Filipe Bezerra
 */
public class FormaPagamentoRepositories {

    private FormaPagamentoRepositories() {/* No instances */}

    private static FormaPagamentoService sService = null;

    private static FormaPagamentoRepository sRepository = null;

    private static RealmMapper<FormaPagamento, FormaPagamentoEntity> sMapper = null;

    public synchronized static FormaPagamentoService getService(@NonNull Context context) {
        if (sService == null) {
            sService = NetworkInjection.provideRetrofit().create(FormaPagamentoService.class);
        }
        return sService;
    }

    public synchronized static RealmMapper<FormaPagamento, FormaPagamentoEntity> getMapper() {
        if (sMapper == null) {
            sMapper = new FormaPagamentoMapper();
        }
        return sMapper;
    }

    public synchronized static FormaPagamentoRepository getRepository() {
        if (sRepository == null) {
            sRepository = new FormaPagamentoRepositoryImpl(getMapper());
        }
        return sRepository;
    }
}
