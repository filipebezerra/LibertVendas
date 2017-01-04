package br.com.libertsolutions.libertvendas.app.data.cidade;

import br.com.libertsolutions.libertvendas.app.data.repository.RealmMapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.EstadoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Estado;

/**
 * @author Filipe Bezerra
 */
public class EstadoRepositories {

    private EstadoRepositories() {/* No instances */}

    private static RealmMapper<Estado, EstadoEntity> sMapper = null;

    private static EstadoRepository sRepository = null;

    public synchronized static RealmMapper<Estado, EstadoEntity> getMapper() {
        if (sMapper == null) {
            sMapper = new EstadoMapper();
        }
        return sMapper;
    }

    public synchronized static EstadoRepository getRepository() {
        if (sRepository == null) {
            sRepository = new EstadoRepositoryImpl(getMapper());
        }
        return sRepository;
    }
}
