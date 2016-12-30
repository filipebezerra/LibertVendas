package br.com.libertsolutions.libertvendas.app.data.cidade;

import br.com.libertsolutions.libertvendas.app.data.repository.RealmMapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.EstadoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Estado;

/**
 * @author Filipe Bezerra
 */
class EstadoMapper extends RealmMapper<Estado, EstadoEntity> {

    @Override public EstadoEntity toEntity(final Estado object) {
        return new EstadoEntity()
                .setId(object.getIdEstado())
                .setNome(object.getNome())
                .setUf(object.getUf());
    }

    @Override public Estado toViewObject(final EstadoEntity entity) {
        Integer id = entity.getId();
        String nome = entity.getNome();
        String uf = entity.getUf();

        return Estado.create(
                id, uf, nome
        );
    }
}
