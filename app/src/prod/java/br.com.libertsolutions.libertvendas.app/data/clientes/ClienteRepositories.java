package br.com.libertsolutions.libertvendas.app.data.clientes;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.domain.entity.ClienteEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;

/**
 * @author Filipe Bezerra
 */
public class ClienteRepositories {
    private ClienteRepositories() {/* No instances */}

    private static Repository<Cliente> sRepository = null;

    private static Mapper<Cliente, ClienteEntity> sMapper = null;

    public synchronized static Repository<Cliente> getRepository(@NonNull Context context) {
        if (sRepository == null) {
            sRepository = new ClienteRepository(context.getApplicationContext());
        }
        return sRepository;
    }

    public synchronized static Mapper<Cliente, ClienteEntity> getMapper() {
        if (sMapper == null) {
            sMapper = new ClienteMapper();
        }
        return sMapper;
    }
}
