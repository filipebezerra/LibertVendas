package br.com.libertsolutions.libertvendas.app.domain.factory;

import br.com.libertsolutions.libertvendas.app.domain.dto.TabelaPrecoDto;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemTabela;
import br.com.libertsolutions.libertvendas.app.domain.pojo.TabelaPreco;
import br.com.libertsolutions.libertvendas.app.domain.util.Preconditions;
import br.com.libertsolutions.libertvendas.app.presentation.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class TabelaPrecoFactory {
    private TabelaPrecoFactory() {/* No instances */}

    public static List<TabelaPreco> createListTabelaPreco(List<TabelaPrecoDto> pDtos) {
        Preconditions.checkNotNull(pDtos, "List<TabelaPrecoDto> can't be null");

        List<TabelaPreco> tabelaPrecoList = new ArrayList<>();

        for (TabelaPrecoDto dto : pDtos) {
            tabelaPrecoList.add(createTabelaPreco(dto));
        }
        return tabelaPrecoList;
    }

    private static TabelaPreco createTabelaPreco(TabelaPrecoDto pDto) {
        Preconditions.checkNotNull(pDto,
                "TabelaPrecoDto can't be null");
        Preconditions.checkState(pDto.idTabela != 0,
                "TabelaPrecoDto.idTabela can't be 0");
        Preconditions.checkState(!StringUtils.isEmpty(pDto.nome),
                "TabelaPrecoDto.nome can't be empty");

        final List<ItemTabela> itensTabela = ItemTabelaFactory
                .createListItemTabela(pDto.itensTabela);

        return new TabelaPreco(
                pDto.idTabela, pDto.codigo, pDto.nome, pDto.ativo,
                pDto.ultimaAlteracao, itensTabela
        );
    }
}
