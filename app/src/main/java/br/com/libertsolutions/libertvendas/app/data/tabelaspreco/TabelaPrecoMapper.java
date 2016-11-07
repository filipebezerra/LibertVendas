package br.com.libertsolutions.libertvendas.app.data.tabelaspreco;

import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.ItemTabelaEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.TabelaPrecoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemTabela;
import br.com.libertsolutions.libertvendas.app.domain.pojo.TabelaPreco;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class TabelaPrecoMapper extends Mapper<TabelaPreco, TabelaPrecoEntity> {
    private final Mapper<ItemTabela, ItemTabelaEntity> mItemTabelaMapper;

    public TabelaPrecoMapper(Mapper<ItemTabela, ItemTabelaEntity> pItemTabelaMapper) {
        mItemTabelaMapper = pItemTabelaMapper;
    }

    @Override
    public TabelaPrecoEntity toEntity(TabelaPreco object) {
        return new TabelaPrecoEntity()
                .setIdTabela(object.getIdTabela())
                .setCodigo(object.getCodigo())
                .setNome(object.getNome())
                .setAtivo(object.isAtivo())
                .setUltimaAlteracao(object.getUltimaAlteracao())
                .setItensTabela(mItemTabelaMapper.toEntityList(object.getItensTabela()));
    }

    @Override
    public TabelaPreco toViewObject(TabelaPrecoEntity entity) {
        final Integer idTabela = entity.getIdTabela();
        final String codigo = entity.getCodigo();
        final String nome = entity.getNome();
        final Boolean ativo = entity.isAtivo();
        final String ultimaAlteracao = entity.getUltimaAlteracao();
        final List<ItemTabela> itensTabela = mItemTabelaMapper
                .toViewObjectList(entity.getItensTabela());

        return new TabelaPreco(
                idTabela, codigo, nome, ativo, ultimaAlteracao, itensTabela
        );
    }
}
