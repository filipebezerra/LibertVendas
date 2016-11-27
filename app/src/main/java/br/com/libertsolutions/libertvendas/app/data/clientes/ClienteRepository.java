package br.com.libertsolutions.libertvendas.app.data.clientes;

import br.com.libertsolutions.libertvendas.app.data.cidades.CidadeRepositories;
import br.com.libertsolutions.libertvendas.app.data.repository.AbstractRealmRepository;
import br.com.libertsolutions.libertvendas.app.domain.entity.ClienteEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;

import static br.com.libertsolutions.libertvendas.app.data.clientes.ClienteRepositories.getEntityMapper;

/**
 * @author Filipe Bezerra
 */
public class ClienteRepository extends AbstractRealmRepository<Cliente, ClienteEntity> {
    public ClienteRepository() {
        super(ClienteEntity.class, getEntityMapper(CidadeRepositories.getEntityMapper()));
    }
}
