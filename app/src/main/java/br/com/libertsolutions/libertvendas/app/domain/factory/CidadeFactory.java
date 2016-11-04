package br.com.libertsolutions.libertvendas.app.domain.factory;

import br.com.libertsolutions.libertvendas.app.domain.dto.CidadeDto;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cidade;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Estado;
import br.com.libertsolutions.libertvendas.app.domain.util.Preconditions;
import br.com.libertsolutions.libertvendas.app.presentation.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class CidadeFactory {
    private CidadeFactory() {/* No instances */}

    public static List<Cidade> createListCidade(List<CidadeDto> pDtos) {
        Preconditions.checkNotNull(pDtos, "List<CidadeDto> can't be null");

        List<Cidade> cidadeList = new ArrayList<>();

        for (CidadeDto dto : pDtos) {
            Cidade cidade = createCidade(dto);
            if (cidade != null) {
                cidadeList.add(cidade);
            }
        }
        return cidadeList;
    }

    public static Cidade createCidade(CidadeDto pDto) {
        Preconditions.checkNotNull(
                pDto, "CidadeDto can't be null");
        Preconditions.checkState(
                pDto.idCidade != 0, "CidadeDto.idCidade can't be 0");
        Preconditions.checkState(
                pDto.estado != null, "CidadeDto.estado can't be null");


        if (StringUtils.isEmpty(pDto.nome)) {
            return null;
        }

        final Estado estado = EstadoFactory.createEstado(pDto.estado);

        return new Cidade(
                pDto.idCidade, pDto.codMunicipio, pDto.nome, estado
        );
    }
}
