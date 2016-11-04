package br.com.libertsolutions.libertvendas.app.domain.factory;

import br.com.libertsolutions.libertvendas.app.domain.dto.EstadoDto;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Estado;
import br.com.libertsolutions.libertvendas.app.domain.util.Preconditions;
import br.com.libertsolutions.libertvendas.app.presentation.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class EstadoFactory {
    private EstadoFactory() {/* No instances */}

    public static List<Estado> createListEstado(List<EstadoDto> pDtos) {
        Preconditions.checkNotNull(pDtos, "List<EstadoDto> can't be null");

        List<Estado> estadoList = new ArrayList<>();

        for (EstadoDto dto : pDtos) {
            estadoList.add(createEstado(dto));
        }
        return estadoList;
    }

    public static Estado createEstado(EstadoDto pDto) {
        Preconditions.checkNotNull(
                pDto, "EstadoDto can't be null");
        Preconditions.checkState(
                pDto.idEstado != 0, "EstadoDto.idEstado can't be 0");
        Preconditions.checkState(
                !StringUtils.isEmpty(pDto.uf), "EstadoDto.uf can't be empty");
        Preconditions.checkState(
                !StringUtils.isEmpty(pDto.nome), "EstadoDto.nome can't be empty");

        return new Estado(
                pDto.idEstado, pDto.uf, pDto.nome
        );
    }
}
