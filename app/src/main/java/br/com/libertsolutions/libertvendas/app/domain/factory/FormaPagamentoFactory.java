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

    public static List<FormaPagamento> createListFormaPagamento(List<FormaPagamentoDto> pDtos) {
        List<FormaPagamento> formaPagamentoList = new ArrayList<>();

        for (FormaPagamentoDto dto : pDtos) {
            formaPagamentoList.add(createFormaPagamento(dto));
        }
        return formaPagamentoList;
    }

    public static FormaPagamento createFormaPagamento(FormaPagamentoDto pDto) {
        return FormaPagamento.create(
                pDto.idFormPgto, pDto.codigo, pDto.descricao, pDto.perDesc, pDto.idEmpresa,
                pDto.ultimaAlteracao, pDto.ativo
        );
    }
}
