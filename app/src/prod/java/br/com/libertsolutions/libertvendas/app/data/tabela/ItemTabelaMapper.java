package br.com.libertsolutions.libertvendas.app.data.tabela;

import br.com.libertsolutions.libertvendas.app.data.repository.RealmMapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.ItemTabelaEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.ProdutoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemTabela;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Produto;

/**
 * @author Filipe Bezerra
 */
class ItemTabelaMapper extends RealmMapper<ItemTabela, ItemTabelaEntity> {

    private final RealmMapper<Produto, ProdutoEntity> mProdutoMapper;

    ItemTabelaMapper(final RealmMapper<Produto, ProdutoEntity> produtoMapper) {
        mProdutoMapper = produtoMapper;
    }

    @Override public ItemTabelaEntity toEntity(final ItemTabela object) {
        return new ItemTabelaEntity()
                .setId(object.getIdItemTabela())
                .setPrecoVenda(object.getPrecoVenda())
                .setIdProduto(object.getIdProduto())
                .setUltimaAlteracao(object.getUltimaAlteracao())
                .setProduto(mProdutoMapper.toEntity(object.getProduto()));
    }

    @Override public ItemTabela toViewObject(final ItemTabelaEntity entity) {
        final Integer idItemTabela = entity.getId();
        final Double precoVenda = entity.getPrecoVenda();
        final Integer idProduto = entity.getIdProduto();
        final String ultimaAlteracao = entity.getUltimaAlteracao();
        final Produto produto = mProdutoMapper.toViewObject(entity.getProduto());

        return ItemTabela.create(
                idItemTabela, precoVenda, idProduto, ultimaAlteracao, produto
        );
    }
}
