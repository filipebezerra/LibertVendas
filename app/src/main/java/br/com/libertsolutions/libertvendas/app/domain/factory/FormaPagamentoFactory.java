package br.com.libertsolutions.libertvendas.app.domain.factory;

import br.com.libertsolutions.libertvendas.app.domain.dto.FormaPagamentoDto;
import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import br.com.libertsolutions.libertvendas.app.domain.util.Preconditions;
import br.com.libertsolutions.libertvendas.app.presentation.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class FormaPagamentoFactory {
    private FormaPagamentoFactory() {/* No instances */}

    public static List<FormaPagamento> createListFormaPagamento(List<FormaPagamentoDto> pDtos) {
        Preconditions.checkNotNull(pDtos, "List<FormaPagamentoDto> can't be null");

        List<FormaPagamento> formaPagamentoList = new ArrayList<>();

        for (FormaPagamentoDto dto : pDtos) {
            formaPagamentoList.add(createFormaPagamento(dto));
        }
        return formaPagamentoList;
    }

    public static FormaPagamento createFormaPagamento(FormaPagamentoDto pDto) {
        Preconditions.checkNotNull(
                pDto, "FormaPagamentoDto can't be null");
        Preconditions.checkState(
                pDto.idFormPgto != 0, "FormaPagamentoDto.idFormPgto can't be 0");
        Preconditions.checkState(
                !StringUtils.isEmpty(pDto.descricao), "FormaPagamentoDto.descricao can't be empty");

        return new FormaPagamento(
                pDto.idFormPgto, pDto.codigo, pDto.descricao, pDto.perDesc, pDto.idEmpresa,
                pDto.ultimaAlteracao, pDto.ativo
        );
    }
}
