package br.com.libertsolutions.libertvendas.app.data.cidades;

import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.EstadoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Estado;

/**
 * @author Filipe Bezerra
 */
public class EstadoRepositories {
    private EstadoRepositories() {/* No instances */}

    private static EstadoRepository sRepository = null;

    private static Mapper<Estado, EstadoEntity> sMapper = null;

    public static synchronized EstadoRepository getRepository() {
        if (sRepository == null) {
            sRepository = new EstadoRepository(getEstadoMapper());
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
