package br.com.libertsolutions.libertvendas.app.data.pedidos;

import br.com.libertsolutions.libertvendas.app.data.repository.AbstractRealmRepository;
import br.com.libertsolutions.libertvendas.app.domain.entity.PedidoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;

/**
 * @author Filipe Bezerra
 */
public class PedidoRepository extends AbstractRealmRepository<Pedido, PedidoEntity> {
    public PedidoRepository() {
        super(PedidoEntity.class, PedidoRepositories.getEntityMapper());
    }
}
