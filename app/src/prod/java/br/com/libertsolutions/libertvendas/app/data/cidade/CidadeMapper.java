package br.com.libertsolutions.libertvendas.app.data.cidade;

import br.com.libertsolutions.libertvendas.app.data.repository.RealmMapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.CidadeEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.EstadoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cidade;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Estado;

/**
 * @author Filipe Bezerra
 */
public class CidadeMapper extends RealmMapper<Cidade, CidadeEntity> {

    private final RealmMapper<Estado, EstadoEntity> mEstadoMapper;

    public CidadeMapper(final RealmMapper<Estado, EstadoEntity> estadoMapper) {
        mEstadoMapper = estadoMapper;
    }

    @Override public CidadeEntity toEntity(final Cidade object) {
        return new CidadeEntity()
                .setId(object.getIdCidade())
                .setNome(object.getNome())
                .setCodMunicipio(object.getCodMunicipio())
                .setEstado(mEstadoMapper.toEntity(object.getEstado()));
    }

    @Override public Cidade toViewObject(final CidadeEntity entity) {
        Integer id = entity.getId();
        String nome = entity.getNome();
        String codMunicipio = entity.getCodMunicipio();
        Estado estado = mEstadoMapper.toViewObject(entity.getEstado());

        return Cidade.create(
                id, codMunicipio, nome, estado
        );
    }
}
