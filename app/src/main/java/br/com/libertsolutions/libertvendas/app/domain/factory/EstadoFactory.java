package br.com.libertsolutions.libertvendas.app.domain.factory;

import br.com.libertsolutions.libertvendas.app.domain.dto.EstadoDto;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Estado;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
class EstadoFactory {

    private EstadoFactory() {/* No instances */}

    public static List<Estado> createListEstado(List<EstadoDto> pDtos) {
        List<Estado> estadoList = new ArrayList<>();

        for (EstadoDto dto : pDtos) {
            estadoList.add(createEstado(dto));
        }
        return estadoList;
    }

    public static Estado createEstado(EstadoDto pDto) {
        return Estado.create(
                pDto.idEstado, pDto.uf, pDto.nome
        );
    }
}
