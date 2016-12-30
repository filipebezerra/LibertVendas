package br.com.libertsolutions.libertvendas.app.domain.factory;

import br.com.libertsolutions.libertvendas.app.domain.dto.TabelaDto;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemTabela;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Tabela;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class TabelaFactory {

    private TabelaFactory() {/* No instances */}

    public static List<Tabela> createListTabelaPreco(List<TabelaDto> pDtos) {
        List<Tabela> tabelaPrecoList = new ArrayList<>();

        for (TabelaDto dto : pDtos) {
            tabelaPrecoList.add(createTabelaPreco(dto));
        }
        return tabelaPrecoList;
    }

    private static Tabela createTabelaPreco(TabelaDto pDto) {
        final List<ItemTabela> itensTabela = ItemTabelaFactory
                .createListItemTabela(pDto.itensTabela);

        return Tabela.create(
                pDto.idTabela, pDto.codigo, pDto.nome, pDto.ativo,
                pDto.ultimaAlteracao, itensTabela
        );
    }
}
