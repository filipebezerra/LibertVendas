package br.com.libertsolutions.libertvendas.app.data.clientes;

import android.content.Context;
import br.com.libertsolutions.libertvendas.app.data.repository.AbstractRealmRepository;
import br.com.libertsolutions.libertvendas.app.domain.entity.ClienteEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;

/**
 * @author Filipe Bezerra
 */
public class ClienteRepository extends AbstractRealmRepository<Cliente, ClienteEntity> {
    public ClienteRepository(Context context) {
        super(context, ClienteEntity.class, new ClienteMapper());
    }
}
