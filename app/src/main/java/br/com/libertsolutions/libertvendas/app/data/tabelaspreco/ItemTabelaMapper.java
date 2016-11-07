package br.com.libertsolutions.libertvendas.app.data.tabelaspreco;

import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.ItemTabelaEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemTabela;

/**
 * @author Filipe Bezerra
 */
public class ItemTabelaMapper extends Mapper<ItemTabela, ItemTabelaEntity> {
    @Override
    public ItemTabelaEntity toEntity(ItemTabela object) {
        return new ItemTabelaEntity()
                .setIdItemTabela(object.getIdItemTabela())
                .setPrecoVenda(object.getPrecoVenda())
                .setIdProduto(object.getIdProduto())
                .setUltimaAlteracao(object.getUltimaAlteracao());
    }

    @Override
    public ItemTabela toViewObject(ItemTabelaEntity entity) {
        final Integer idItemTabela = entity.getIdItemTabela();
        final Double precoVenda = entity.getPrecoVenda();
        final Integer idProduto = entity.getIdProduto();
        final String ultimaAlteracao = entity.getUltimaAlteracao();

        return new ItemTabela(
              idItemTabela, precoVenda, idProduto, ultimaAlteracao
        );
    }
}
