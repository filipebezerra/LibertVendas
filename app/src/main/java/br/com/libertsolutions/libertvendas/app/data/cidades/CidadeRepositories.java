package br.com.libertsolutions.libertvendas.app.data.cidades;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.data.util.ServiceFactory;
import br.com.libertsolutions.libertvendas.app.domain.entity.CidadeEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.EstadoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cidade;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Estado;

/**
 * @author Filipe Bezerra
 */
public class CidadeRepositories {
    private CidadeRepositories() {/* No instances */}

    private static CidadeService sService = null;

    private static Repository<Cidade> sRepository = null;

    private static Mapper<Cidade, CidadeEntity> sMapper = null;

    private static Mapper<Estado, EstadoEntity> sEstadoMapper = null;

    public static synchronized CidadeService getService(@NonNull Context pContext) {
        if (sService == null) {
            sService = ServiceFactory.createService(pContext, CidadeService.class);
        }
        return sService;
    }

    public static synchronized Repository<Cidade> getRepository(@NonNull Context pContext) {
        if (sRepository == null) {
            sRepository = new CidadeRepository(pContext, getMapper());
        }
        return sRepository;
    }

    private static Mapper<Cidade, CidadeEntity> getMapper() {
        if (sMapper == null) {
            sMapper = new CidadeMapper(getEstadoMapper());
        }
        return sMapper;
    }

    private static Mapper<Estado, EstadoEntity> getEstadoMapper() {
        if (sEstadoMapper == null) {
            sEstadoMapper = new EstadoMapper();
        }
        return sEstadoMapper;
    }
}
