package br.com.libertsolutions.libertvendas.app.data.pedidos;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.data.cidades.CidadeRepositories;
import br.com.libertsolutions.libertvendas.app.data.clientes.ClienteRepositories;
import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.domain.entity.PedidoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;

/**
 * @author Filipe Bezerra
 */
public class PedidoRepositories {
    private PedidoRepositories() {/* No instances */}

    private static Repository<Pedido> sRepository = null;

    private static Mapper<Pedido, PedidoEntity> sMapper = null;

    public synchronized static Repository<Pedido> getRepository(@NonNull Context context) {
        if (sRepository == null) {
            sRepository = new PedidoRepository(context.getApplicationContext());
        }
        return sRepository;
    }

    public synchronized static Mapper<Pedido, PedidoEntity> getEntityMapper() {
        if (sMapper == null) {
            sMapper = new PedidoMapper(
                    ClienteRepositories.getEntityMapper(
                            CidadeRepositories.getEntityMapper()));
        }
        return sMapper;
    }
}
