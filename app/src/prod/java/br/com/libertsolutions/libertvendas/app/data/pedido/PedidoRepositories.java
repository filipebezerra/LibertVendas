package br.com.libertsolutions.libertvendas.app.data.pedido;

import br.com.libertsolutions.libertvendas.app.data.cliente.ClienteRepositories;
import br.com.libertsolutions.libertvendas.app.data.formapagamento.FormaPagamentoRepositories;
import br.com.libertsolutions.libertvendas.app.data.repository.RealmMapper;
import br.com.libertsolutions.libertvendas.app.data.tabela.TabelaRepositories;
import br.com.libertsolutions.libertvendas.app.domain.entity.ItemPedidoEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.PedidoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemPedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;

import static br.com.libertsolutions.libertvendas.app.NetworkInjection.provideRetrofit;

/**
 * @author Filipe Bezerra
 */
public class PedidoRepositories {

    private PedidoRepositories() {/* No instances */}

    private static PedidoService sService = null;

    private static PedidoRepository sRepository = null;

    private static RealmMapper<Pedido, PedidoEntity> sMapper = null;

    private static RealmMapper<ItemPedido, ItemPedidoEntity> sItemMapper = null;

    public static PedidoService getService() {
        if (sService == null) {
            sService = provideRetrofit().create(PedidoService.class);
        }
        return sService;
    }

    public synchronized static PedidoRepository getRepository() {
        if (sRepository == null) {
            sRepository = new PedidoRepositoryImpl(getMapper());
        }
        return sRepository;
    }

    public synchronized static RealmMapper<Pedido, PedidoEntity> getMapper() {
        if (sMapper == null) {
            sMapper = new PedidoMapper(
                    ClienteRepositories.getMapper(),
                    FormaPagamentoRepositories.getMapper(),
                    TabelaRepositories.getMapper(),
                    getItemMapper());
        }
        return sMapper;
    }

    private static RealmMapper<ItemPedido, ItemPedidoEntity> getItemMapper() {
        if (sItemMapper == null) {
            sItemMapper = new ItemPedidoMapper(TabelaRepositories.getProdutoMapper());
        }
        return sItemMapper;
    }
}
