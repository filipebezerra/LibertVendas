package br.com.libertsolutions.libertvendas.app.data.cidades;

import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.EstadoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Estado;

/**
 * @author Filipe Bezerra
 */
public class EstadoMapper extends Mapper<Estado, EstadoEntity> {
    @Override
    public EstadoEntity toEntity(Estado object) {
        return new EstadoEntity()
                .setIdEstado(object.getIdEstado())
                .setUf(object.getUf())
                .setNome(object.getNome());
    }

    @Override
    public Estado toViewObject(EstadoEntity entity) {
        final int idEstado = entity.getIdEstado();
        final String uf = entity.getUf();
        final String nome = entity.getNome();

        return new Estado(
            idEstado, uf, nome
        );
    }
}
