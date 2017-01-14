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

    public static List<Cidade> toPojoList(final List<CidadeDto> cidadeDtos) {
        List<Cidade> cidadeList = new ArrayList<>();

        for (CidadeDto dto : cidadeDtos) {
            Cidade cidade = toPojo(dto);
            if (cidade != null) {
                cidadeList.add(cidade);
            }
        }
        return cidadeList;
    }

    static Cidade toPojo(final CidadeDto cidadeDto) {
        final Estado estado = EstadoFactory.toPojo(cidadeDto.estado);

        return Cidade.create(
                cidadeDto.idCidade, cidadeDto.codMunicipio, cidadeDto.nome, estado
        );
    }

    static CidadeDto toDto(final CidadeEntity cidadeEntity) {
        CidadeDto cidadeDto = new CidadeDto();
        cidadeDto.idCidade = cidadeEntity.getId();
        cidadeDto.nome = cidadeEntity.getNome();
        cidadeDto.codMunicipio = cidadeEntity.getCodMunicipio();
        cidadeDto.estado = EstadoFactory.toDto(cidadeEntity.getEstado());
        return cidadeDto;
    }
}
