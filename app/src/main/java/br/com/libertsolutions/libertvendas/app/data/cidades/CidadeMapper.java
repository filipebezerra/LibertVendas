package br.com.libertsolutions.libertvendas.app.data.cidades;

import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.CidadeEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.EstadoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cidade;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Estado;

/**
 * @author Filipe Bezerra
 */
public class CidadeMapper extends Mapper<Cidade, CidadeEntity> {
    private final Mapper<Estado, EstadoEntity> mEstadoMapper;

    public CidadeMapper(Mapper<Estado, EstadoEntity> pEstadoMapper) {
        mEstadoMapper = pEstadoMapper;
    }

    @Override
    public CidadeEntity toEntity(Cidade object) {
        return new CidadeEntity()
                .setIdCidade(object.getIdCidade())
                .setCodMunicipio(object.getCodMunicipio())
                .setNome(object.getNome())
                .setEstado(mEstadoMapper.toEntity(object.getEstado()));
    }

    @Override
    public Cidade toViewObject(CidadeEntity entity) {
        final int idCidade = entity.getIdCidade();
        final String codMunicipio = entity.getCodMunicipio();
        final String nome = entity.getNome();
        final Estado estado = mEstadoMapper.toViewObject(entity.getEstado());

        return new Cidade(
                idCidade, codMunicipio, nome, estado
        );
    }
}
