package br.com.libertsolutions.libertvendas.app.domain.factory;

import br.com.libertsolutions.libertvendas.app.domain.dto.EstadoDto;
import br.com.libertsolutions.libertvendas.app.domain.entity.EstadoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Estado;

/**
 * @author Filipe Bezerra
 */
class EstadoFactory {

    private EstadoFactory() {/* No instances */}

    static Estado toPojo(final EstadoDto estadoDto) {
        return Estado.create(
                estadoDto.idEstado, estadoDto.uf, estadoDto.nome
        );
    }

    static EstadoDto toDto(final EstadoEntity estadoEntity) {
        EstadoDto estadoDto = new EstadoDto();
        estadoDto.idEstado = estadoEntity.getId();
        estadoDto.nome = estadoEntity.getNome();
        estadoDto.uf = estadoEntity.getUf();
        return estadoDto;
    }
}
