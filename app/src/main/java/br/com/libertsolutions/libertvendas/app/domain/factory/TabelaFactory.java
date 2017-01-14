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

    public static List<Tabela> toPojoList(
            final List<TabelaDto> tabelaDtos, final String cpfCnpjVendedor,
            final String cnpjEmpresa) {
        List<Tabela> tabelaPrecoList = new ArrayList<>();

        for (TabelaDto dto : tabelaDtos) {
            tabelaPrecoList.add(toPojo(dto, cpfCnpjVendedor, cnpjEmpresa));
        }
        return tabelaPrecoList;
    }

    private static Tabela toPojo(TabelaDto tabelaDto, String cpfCnpjVendedor, String cnpjEmpresa) {
        final List<ItemTabela> itensTabela = ItemTabelaFactory.toPojoList(tabelaDto.itensTabela);

        return Tabela.create(
                tabelaDto.idTabela, tabelaDto.codigo, tabelaDto.nome, tabelaDto.ativo,
                tabelaDto.ultimaAlteracao, itensTabela, cpfCnpjVendedor, cnpjEmpresa
        );
    }
}
