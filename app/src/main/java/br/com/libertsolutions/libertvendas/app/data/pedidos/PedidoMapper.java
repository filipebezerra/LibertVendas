package br.com.libertsolutions.libertvendas.app.data.pedidos;

import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.ClienteEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.PedidoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;

/**
 * @author Filipe Bezerra
 */
class PedidoMapper extends Mapper<Pedido,PedidoEntity> {
    private final Mapper<Cliente, ClienteEntity> mClienteMapper;

    PedidoMapper(Mapper<Cliente, ClienteEntity> pClienteMapper) {
        mClienteMapper = pClienteMapper;
    }

    @Override
    public PedidoEntity toEntity(Pedido object) {
        return new PedidoEntity()
                .setNumero(object.getNumero())
                .setCliente(mClienteMapper.toEntity(object.getCliente()))
                .setTotal(object.getTotal())
                .setDataEmissao(object.getDataEmissao())
                .setStatus(object.getStatus());
    }

    @Override
    public Pedido toViewObject(PedidoEntity entity) {
        final int numero = entity.getNumero();
        final Cliente cliente = mClienteMapper.toViewObject(entity.getCliente());
        final double total = entity.getTotal();
        final long dataEmissao = entity.getDataEmissao();
        final int status = entity.getStatus();
        return new Pedido(
                numero, cliente, total, dataEmissao, status
        );
    }
}
