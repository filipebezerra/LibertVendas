package br.com.libertsolutions.libertvendas.app.data.formaspagamento;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.data.util.ServiceFactory;
import br.com.libertsolutions.libertvendas.app.domain.entity.FormaPagamentoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;

/**
 * @author Filipe Bezerra
 */
public class FormaPagamentoRepositories {
    private FormaPagamentoRepositories() {/* No instances */}

    private static FormaPagamentoService sService = null;

    private static Repository<FormaPagamento> sRepository = null;

    private static Mapper<FormaPagamento, FormaPagamentoEntity> sMapper = null;

    public static synchronized FormaPagamentoService getService(@NonNull Context pContext) {
        if (sService == null) {
            sService = ServiceFactory.createService(pContext, FormaPagamentoService.class);
        }
        return sService;
    }

    public static synchronized Repository<FormaPagamento> getRepository(@NonNull Context pContext) {
        if (sRepository == null) {
            sRepository = new FormaPagamentoRepository(pContext, getMapper());
        }
        return sRepository;
    }

    private static Mapper<FormaPagamento, FormaPagamentoEntity> getMapper() {
        if (sMapper == null) {
            sMapper = new FormaPagamentoMapper();
        }
        return sMapper;
    }
}
