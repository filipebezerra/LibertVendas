package br.com.libertsolutions.libertvendas.app.data.tabela;

import br.com.libertsolutions.libertvendas.app.data.repository.RealmMapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.ItemTabelaEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.TabelaEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemTabela;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Tabela;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
class TabelaMapper extends RealmMapper<Tabela, TabelaEntity> {

    private final RealmMapper<ItemTabela, ItemTabelaEntity> mItemTabelaMapper;

    TabelaMapper(RealmMapper<ItemTabela, ItemTabelaEntity> itemTabelaMapper) {
        mItemTabelaMapper = itemTabelaMapper;
    }

    @Override public TabelaEntity toEntity(final Tabela object) {
        return new TabelaEntity()
                .setId(object.getIdTabela())
                .setCodigo(object.getCodigo())
                .setNome(object.getNome())
                .setAtivo(object.isAtivo())
                .setUltimaAlteracao(object.getUltimaAlteracao())
                .setItensTabela(mItemTabelaMapper.toEntityList(object.getItensTabela()));
    }

    @Override public Tabela toViewObject(final TabelaEntity entity) {
        final Integer idTabela = entity.getId();
        final String codigo = entity.getCodigo();
        final String nome = entity.getNome();
        final Boolean ativo = entity.isAtivo();
        final String ultimaAlteracao = entity.getUltimaAlteracao();
        final List<ItemTabela> itensTabela
                = mItemTabelaMapper.toViewObjectList(entity.getItensTabela());
        String cpfCnpjVendedor = entity.getCpfCnpjVendedor();
        String cnpjEmpresa = entity.getCnpjEmpresa();

        return Tabela.create(
                idTabela, codigo, nome, ativo, ultimaAlteracao, itensTabela, cpfCnpjVendedor,
                cnpjEmpresa
        );
    }
}
