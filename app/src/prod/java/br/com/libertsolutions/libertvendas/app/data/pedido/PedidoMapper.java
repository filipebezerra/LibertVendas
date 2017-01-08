package br.com.libertsolutions.libertvendas.app.data.pedido;

import br.com.libertsolutions.libertvendas.app.data.repository.RealmMapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.ClienteEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.FormaPagamentoEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.ItemPedidoEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.PedidoEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.TabelaEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemPedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Tabela;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class PedidoMapper extends RealmMapper<Pedido, PedidoEntity> {

    private final RealmMapper<Cliente, ClienteEntity> mClienteEntityMapper;
    private final RealmMapper<FormaPagamento, FormaPagamentoEntity> mFormaPagamentoEntityMapper;
    private final RealmMapper<Tabela, TabelaEntity> mTabelaEntityMapper;
    private final RealmMapper<ItemPedido, ItemPedidoEntity> mItemPedidoEntityMapper;

    public PedidoMapper(
            final RealmMapper<Cliente, ClienteEntity> clienteEntityMapper,
            final RealmMapper<FormaPagamento, FormaPagamentoEntity> formaPagamentoEntityMapper,
            final RealmMapper<Tabela, TabelaEntity> tabelaEntityMapper,
            final RealmMapper<ItemPedido, ItemPedidoEntity> itemPedidoEntityMapper) {
        mClienteEntityMapper = clienteEntityMapper;
        mFormaPagamentoEntityMapper = formaPagamentoEntityMapper;
        mTabelaEntityMapper = tabelaEntityMapper;
        mItemPedidoEntityMapper = itemPedidoEntityMapper;
    }

    @Override public PedidoEntity toEntity(final Pedido object) {
        return new PedidoEntity()
                .setId(object.getId())
                .setIdPedido(object.getIdPedido())
                .setTipo(object.getTipo())
                .setStatus(object.getStatus())
                .setDataEmissao(object.getDataEmissao())
                .setDesconto(object.getDesconto())
                .setPercentualDesconto(object.getPercentualDesconto())
                .setObservacao(object.getObservacao())
                .setCliente(mClienteEntityMapper.toEntity(object.getCliente()))
                .setFormaPagamento(mFormaPagamentoEntityMapper.toEntity(object.getFormaPagamento()))
                .setTabela(mTabelaEntityMapper.toEntity(object.getTabela()))
                .setItens(mItemPedidoEntityMapper.toEntityList(object.getItens()))
                .setUltimaAlteracao(object.getUltimaAlteracao())
                .setCnpjEmpresa(object.getCnpjEmpresa())
                .setCpfCnpjVendedor(object.getCpfCnpjVendedor());
    }

    @Override public Pedido toViewObject(final PedidoEntity entity) {
        String id = entity.getId();
        Integer idPedido = entity.getIdPedido();
        Integer tipo = entity.getTipo();
        Integer status = entity.getStatus();
        Long dataEmissao = entity.getDataEmissao();
        Double desconto = entity.getDesconto();
        Float percentualDesconto = entity.getPercentualDesconto();
        String observacao = entity.getObservacao();
        Cliente cliente = mClienteEntityMapper.toViewObject(entity.getCliente());
        FormaPagamento formaPagamento = mFormaPagamentoEntityMapper
                .toViewObject(entity.getFormaPagamento());
        Tabela tabelaPreco = mTabelaEntityMapper.toViewObject(entity.getTabela());
        List<ItemPedido> itemPedidos = mItemPedidoEntityMapper.toViewObjectList(entity.getItens());
        String ultimaAlteracao = entity.getUltimaAlteracao();
        String cnpjEmpresa = entity.getCnpjEmpresa();
        String cpfCnpjVendedor = entity.getCpfCnpjVendedor();

        return Pedido.map(
                id, idPedido, tipo, status, dataEmissao, desconto, percentualDesconto,
                observacao, cliente, formaPagamento, tabelaPreco, itemPedidos, ultimaAlteracao,
                cnpjEmpresa, cpfCnpjVendedor
        );
    }
}
