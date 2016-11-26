package br.com.libertsolutions.libertvendas.app.data.pedidos;

import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.ClienteEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.FormaPagamentoEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.ItemPedidoEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.PedidoEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.TabelaPrecoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemPedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.TabelaPreco;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
class PedidoMapper extends Mapper<Pedido,PedidoEntity> {
    private final Mapper<Cliente, ClienteEntity> mClienteEntityMapper;
    private final Mapper<FormaPagamento, FormaPagamentoEntity> mFormaPagamentoEntityMapper;
    private final Mapper<TabelaPreco, TabelaPrecoEntity> mTabelaPrecoEntityMapper;
    private final Mapper<ItemPedido, ItemPedidoEntity> mItemPedidoEntityMapper;

    PedidoMapper(
            Mapper<Cliente, ClienteEntity> pClienteEntityMapper,
            Mapper<FormaPagamento, FormaPagamentoEntity> pFormaPagamentoEntityMapper,
            Mapper<TabelaPreco, TabelaPrecoEntity> pTabelaPrecoEntityMapper,
            Mapper<ItemPedido, ItemPedidoEntity> pItemPedidoEntityMapper) {
        mClienteEntityMapper = pClienteEntityMapper;
        mFormaPagamentoEntityMapper = pFormaPagamentoEntityMapper;
        mTabelaPrecoEntityMapper = pTabelaPrecoEntityMapper;
        mItemPedidoEntityMapper = pItemPedidoEntityMapper;
    }

    @Override public PedidoEntity toEntity(Pedido object) {
        return new PedidoEntity()
                .setId(object.getId())
                .setIdPedido(object.getIdPedido())
                .setTipo(object.getTipo())
                .setNumero(object.getNumero())
                .setStatus(object.getStatus())
                .setDataEmissao(object.getDataEmissao())
                .setDesconto(object.getDesconto())
                .setPercentualDesconto(object.getPercentualDesconto())
                .setObservacao(object.getObservacao())
                .setCliente(mClienteEntityMapper.toEntity(object.getCliente()))
                .setFormaPagamento(mFormaPagamentoEntityMapper.toEntity(object.getFormaPagamento()))
                .setTabelaPreco(mTabelaPrecoEntityMapper.toEntity(object.getTabelaPreco()))
                .setItens(mItemPedidoEntityMapper.toEntityList(object.getItens()))
                .setUltimaAlteracao(object.getUltimaAlteracao())
                .setCnpjEmpresa(object.getCnpjEmpresa())
                .setCpfCnpjVendedor(object.getCpfCnpjVendedor());
    }

    @Override public Pedido toViewObject(PedidoEntity entity) {
        Integer id = entity.getId();
        Integer idPedido = entity.getIdPedido();
        Integer tipo = entity.getTipo();
        String numero = entity.getNumero();
        Integer status = entity.getStatus();
        Long dataEmissao = entity.getDataEmissao();
        Double desconto = entity.getDesconto();
        Float percentualDesconto = entity.getPercentualDesconto();
        String observacao = entity.getObservacao();
        Cliente cliente = mClienteEntityMapper.toViewObject(entity.getCliente());
        FormaPagamento formaPagamento = mFormaPagamentoEntityMapper.toViewObject(
                entity.getFormaPagamento());
        TabelaPreco tabelaPreco = mTabelaPrecoEntityMapper.toViewObject(entity.getTabelaPreco());
        List<ItemPedido> itemPedidos = mItemPedidoEntityMapper.toViewObjectList(entity.getItens());
        String ultimaAlteracao = entity.getUltimaAlteracao();
        String cnpjEmpresa = entity.getCnpjEmpresa();
        String cpfCnpjVendedor = entity.getCpfCnpjVendedor();

        return new Pedido(
                id, idPedido, tipo, numero, status, dataEmissao, desconto, percentualDesconto,
                observacao, cliente, formaPagamento, tabelaPreco, itemPedidos, ultimaAlteracao,
                cnpjEmpresa, cpfCnpjVendedor
        );
    }
}
