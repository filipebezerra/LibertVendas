package br.com.libertsolutions.libertvendas.app.data.pedidos;

import android.content.Context;
import br.com.libertsolutions.libertvendas.app.data.repository.AbstractRealmRepository;
import br.com.libertsolutions.libertvendas.app.domain.entity.PedidoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;

/**
 * @author Filipe Bezerra
 */
public class PedidoRepository extends AbstractRealmRepository<Pedido, PedidoEntity> {
    public PedidoRepository(Context context) {
        super(context, PedidoEntity.class, PedidoRepositories.getMapper());
    }
}
