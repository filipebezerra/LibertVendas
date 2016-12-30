package br.com.libertsolutions.libertvendas.app.data.cliente;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.data.cidade.CidadeRepositories;
import br.com.libertsolutions.libertvendas.app.data.repository.RealmMapper;
import br.com.libertsolutions.libertvendas.app.data.utils.ServiceFactory;
import br.com.libertsolutions.libertvendas.app.domain.entity.ClienteEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;

/**
 * @author Filipe Bezerra
 */
public class ClienteRepositories {

    private ClienteRepositories() {/* No instances */}

    private static ClienteService sService = null;

    private static RealmMapper<Cliente, ClienteEntity> sMapper = null;

    private static ClienteRepository sRepository = null;

    public synchronized static ClienteService getService(@NonNull Context pContext) {
        if (sService == null) {
            sService = ServiceFactory.createService(pContext, ClienteService.class);
        }
        return sService;
    }

    public synchronized static RealmMapper<Cliente, ClienteEntity> getMapper() {
        if (sMapper == null) {
            sMapper = new ClienteMapper(CidadeRepositories.getMapper());
        }
        return sMapper;
    }

    public synchronized static ClienteRepository getRepository() {
        if (sRepository == null) {
            sRepository = new ClienteRepositoryImpl(getMapper());
        }
        return sRepository;
    }
}
