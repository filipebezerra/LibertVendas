package br.com.libertsolutions.libertvendas.app.data.formaspagamento;

import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.FormaPagamentoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;

/**
 * @author Filipe Bezerra
 */
public class FormaPagamentoMapper extends Mapper<FormaPagamento, FormaPagamentoEntity> {
    @Override
    public FormaPagamentoEntity toEntity(FormaPagamento object) {
        return new FormaPagamentoEntity()
                .setId(object.getId())
                .setIdFormaPagamento(object.getIdFormaPagamento())
                .setCodigo(object.getCodigo())
                .setDescricao(object.getDescricao())
                .setPercentualDesconto(object.getPercentualDesconto())
                .setIdEmpresa(object.getIdEmpresa())
                .setUltimaAlteracao(object.getUltimaAlteracao())
                .setAtivo(object.isAtivo());
    }

    @Override
    public FormaPagamento toViewObject(FormaPagamentoEntity entity) {
        final Integer id = entity.getId();
        final Integer idFormaPagamento = entity.getIdFormaPagamento();
        final String codigo = entity.getCodigo();
        final String descricao = entity.getDescricao();
        final Double percentualDesconto = entity.getPercentualDesconto();
        final Integer idEmpresa = entity.getIdEmpresa();
        final String ultimaAlteracao = entity.getUltimaAlteracao();
        final Boolean ativo = entity.isAtivo();
        return new FormaPagamento(
                id, idFormaPagamento, codigo, descricao, percentualDesconto, idEmpresa,
                ultimaAlteracao, ativo
        );
    }
}
