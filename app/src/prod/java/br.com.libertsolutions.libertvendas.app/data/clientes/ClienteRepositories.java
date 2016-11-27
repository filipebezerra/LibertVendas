package br.com.libertsolutions.libertvendas.app.data.clientes;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.data.util.ServiceFactory;
import br.com.libertsolutions.libertvendas.app.domain.entity.CidadeEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.ClienteEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cidade;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;

/**
 * @author Filipe Bezerra
 */
public class ClienteRepositories {
    private ClienteRepositories() {/* No instances */}

    private static ClienteService sService = null;

    private static ClienteRepository sRepository = null;

    private static Mapper<Cliente, ClienteEntity> sMapper = null;

    public synchronized static ClienteService getService(@NonNull Context pContext) {
        if (sService == null) {
            sService = ServiceFactory.createService(pContext, ClienteService.class);
        }
        return sService;
    }

    public synchronized static ClienteRepository getRepository() {
        if (sRepository == null) {
            sRepository = new ClienteRepository();
        }
        return sRepository;
    }

    public synchronized static Mapper<Cliente, ClienteEntity> getEntityMapper(
            @NonNull Mapper<Cidade, CidadeEntity> pCidadeEntityMapper) {
        if (sMapper == null) {
            sMapper = new ClienteMapper(pCidadeEntityMapper);
        }
        return sMapper;
    }
}
