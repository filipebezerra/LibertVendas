package br.com.libertsolutions.libertvendas.app.domain.factory;

import br.com.libertsolutions.libertvendas.app.domain.dto.CidadeDto;
import br.com.libertsolutions.libertvendas.app.domain.entity.CidadeEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cidade;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Estado;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public final class CidadeFactory {

    private CidadeFactory() {/* No instances */}

    public static List<Cidade> createListCidade(List<CidadeDto> pDtos) {
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
        final Estado estado = EstadoFactory.createEstado(pDto.estado);

        return Cidade.create(
                pDto.idCidade, pDto.codMunicipio, pDto.nome, estado
        );
    }

    public static CidadeDto createDto(CidadeEntity cidadeEntity) {
        CidadeDto cidadeDto = new CidadeDto();
        cidadeDto.idCidade = cidadeEntity.getId();
        cidadeDto.nome = cidadeEntity.getNome();
        cidadeDto.codMunicipio = cidadeEntity.getCodMunicipio();
        cidadeDto.estado = EstadoFactory.createDto(cidadeEntity.getEstado());
        return cidadeDto;
    }
}
