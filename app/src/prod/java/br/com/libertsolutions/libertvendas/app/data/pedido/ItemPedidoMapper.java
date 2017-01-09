package br.com.libertsolutions.libertvendas.app.data.pedido;

import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.data.repository.RealmMapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.ItemPedidoEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.ProdutoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemPedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Produto;

/**
 * @author Filipe Bezerra
 */
class ItemPedidoMapper extends RealmMapper<ItemPedido, ItemPedidoEntity> {

    private final Mapper<Produto, ProdutoEntity> mProdutoMapper;

    ItemPedidoMapper(final Mapper<Produto, ProdutoEntity> produtoMapper) {
        mProdutoMapper = produtoMapper;
    }

    @Override public ItemPedidoEntity toEntity(final ItemPedido object) {
        return new ItemPedidoEntity()
                .setId(object.getId())
                .setIdItem(object.getIdItem())
                .setPrecoVenda(object.getPrecoVenda())
                .setQuantidade(object.getQuantidade())
                .setSubTotal(object.getSubTotal())
                .setProduto(mProdutoMapper.toEntity(object.getProduto()))
                .setUltimaAlteracao(object.getUltimaAlteracao());
    }

    @Override public ItemPedido toViewObject(final ItemPedidoEntity entity) {
        String id = entity.getId();
        Integer idItem = entity.getIdItem();
        Double precoVenda = entity.getPrecoVenda();
        Float quantidade = entity.getQuantidade();
        Double subTotal = entity.getSubTotal();
        Produto produto = mProdutoMapper.toViewObject(entity.getProduto());
        String ultimaAlteracao = entity.getUltimaAlteracao();

        return ItemPedido.map(
                id, idItem, precoVenda, quantidade, subTotal, produto, ultimaAlteracao
        );
    }
}
