package br.com.libertsolutions.libertvendas.app.data.pedidos;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.data.cidades.CidadeRepositories;
import br.com.libertsolutions.libertvendas.app.data.clientes.ClienteRepositories;
import br.com.libertsolutions.libertvendas.app.data.formaspagamento.FormaPagamentoRepositories;
import br.com.libertsolutions.libertvendas.app.data.produtos.ProdutoRepositories;
import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.data.tabelaspreco.TabelaPrecoRepositories;
import br.com.libertsolutions.libertvendas.app.data.util.ServiceFactory;
import br.com.libertsolutions.libertvendas.app.domain.entity.ItemPedidoEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.PedidoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemPedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;

/**
 * @author Filipe Bezerra
 */
public class PedidoRepositories {
    private PedidoRepositories() {/* No instances */}

    private static PedidoService sService = null;

    private static PedidoRepository sRepository = null;

    private static Mapper<Pedido, PedidoEntity> sMapper = null;

    private static Mapper<ItemPedido, ItemPedidoEntity> sItemPedidoEntityMapper = null;

    public static synchronized PedidoService getService(@NonNull Context pContext) {
        if (sService == null) {
            sService = ServiceFactory.createService(pContext, PedidoService.class);
        }
        return sService;
    }


    public synchronized static PedidoRepository getRepository() {
        if (sRepository == null) {
            sRepository = new PedidoRepository();
        }
        return sRepository;
    }

    public synchronized static Mapper<Pedido, PedidoEntity> getEntityMapper() {
        if (sMapper == null) {
            sMapper = new PedidoMapper(
                    ClienteRepositories.getEntityMapper(CidadeRepositories.getEntityMapper()),
                    FormaPagamentoRepositories.getEntityMapper(),
                    TabelaPrecoRepositories.getEntityMapper(),
                    getItemPedidoEntityMapper());
        }
        return sMapper;
    }

    private synchronized static Mapper<ItemPedido, ItemPedidoEntity> getItemPedidoEntityMapper() {
        if (sItemPedidoEntityMapper == null) {
            sItemPedidoEntityMapper = new ItemPedidoMapper(ProdutoRepositories.getEntityMapper());
        }
        return sItemPedidoEntityMapper;
    }
}
