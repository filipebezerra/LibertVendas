package br.com.libertsolutions.libertvendas.app.data.cidades;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.domain.entity.EstadoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Estado;

/**
 * @author Filipe Bezerra
 */
public class EstadoRepositories {
    private EstadoRepositories() {/* No instances */}

    private static Repository<Estado> sRepository = null;

    private static Mapper<Estado, EstadoEntity> sMapper = null;

    public static synchronized Repository<Estado> getRepository(@NonNull Context pContext) {
        if (sRepository == null) {
            sRepository = new EstadoRepository(pContext, getEstadoMapper());
        }
        return sRepository;
    }

    public static Mapper<Estado, EstadoEntity> getEstadoMapper() {
        if (sMapper == null) {
            sMapper = new EstadoMapper();
        }
        return sMapper;
    }
}
