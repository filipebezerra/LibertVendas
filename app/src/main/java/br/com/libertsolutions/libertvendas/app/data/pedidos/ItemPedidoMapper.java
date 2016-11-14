package br.com.libertsolutions.libertvendas.app.data.pedidos;

import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.ItemPedidoEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.ProdutoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemPedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Produto;

/**
 * @author Filipe Bezerra
 */
public class ItemPedidoMapper extends Mapper<ItemPedido, ItemPedidoEntity> {
    private final Mapper<Produto, ProdutoEntity> mProdutoEntityMapper;

    public ItemPedidoMapper(Mapper<Produto, ProdutoEntity> pProdutoEntityMapper) {
        mProdutoEntityMapper = pProdutoEntityMapper;
    }

    @Override public ItemPedidoEntity toEntity(ItemPedido object) {
        return new ItemPedidoEntity()
                .setId(object.getId())
                .setIdItem(object.getIdItem())
                .setPrecoVenda(object.getPrecoVenda())
                .setQuantidade(object.getQuantidade())
                .setDesconto(object.getDesconto())
                .setSubTotal(object.getSubTotal())
                .setObservacao(object.getObservacao())
                .setProduto(mProdutoEntityMapper.toEntity(object.getProduto()))
                .setUltimaAlteracao(object.getUltimaAlteracao());
    }

    @Override public ItemPedido toViewObject(ItemPedidoEntity entity) {
        Integer id = entity.getId();
        Integer idItem = entity.getIdItem();
        Double precoVenda = entity.getPrecoVenda();
        Float quantidade = entity.getQuantidade();
        Double desconto = entity.getDesconto();
        Double subTotal = entity.getSubTotal();
        String observacao = entity.getObservacao();
        Produto produto = mProdutoEntityMapper.toViewObject(entity.getProduto());
        String ultimaAlteracao = entity.getUltimaAlteracao();

        return new ItemPedido(
                id, idItem, precoVenda, quantidade, desconto, subTotal, observacao,
                produto, ultimaAlteracao
        );
    }
}
