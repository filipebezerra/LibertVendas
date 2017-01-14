package br.com.libertsolutions.libertvendas.app.domain.factory;

import br.com.libertsolutions.libertvendas.app.domain.dto.FormaPagamentoDto;
import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public final class FormaPagamentoFactory {

    private FormaPagamentoFactory() {/* No instances */}

    public static List<FormaPagamento> toPojoList(
            final List<FormaPagamentoDto> formaPagamentoDtos, final String cpfCnpjVendedor,
            final String cnpjEmpresa) {
        List<FormaPagamento> formaPagamentoList = new ArrayList<>();

        for (FormaPagamentoDto dto : formaPagamentoDtos) {
            formaPagamentoList.add(toPojo(dto, cpfCnpjVendedor, cnpjEmpresa));
        }
        return formaPagamentoList;
    }

    private static FormaPagamento toPojo(
            FormaPagamentoDto formaPagamentoDto, String cpfCnpjVendedor, String cnpjEmpresa) {
        return FormaPagamento.create(
                formaPagamentoDto.idFormPgto, formaPagamentoDto.codigo, formaPagamentoDto.descricao,
                formaPagamentoDto.perDesc, formaPagamentoDto.idEmpresa,
                formaPagamentoDto.ultimaAlteracao, formaPagamentoDto.ativo,
                cpfCnpjVendedor, cnpjEmpresa
        );
    }
}
