package br.com.libertsolutions.libertvendas.app.data.formapagamento;

import br.com.libertsolutions.libertvendas.app.data.repository.RealmMapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.FormaPagamentoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;

/**
 * @author Filipe Bezerra
 */
class FormaPagamentoMapper extends RealmMapper<FormaPagamento, FormaPagamentoEntity> {

    @Override public FormaPagamentoEntity toEntity(final FormaPagamento object) {
        return new FormaPagamentoEntity()
                .setId(object.getIdFormaPagamento())
                .setCodigo(object.getCodigo())
                .setDescricao(object.getDescricao())
                .setPercentualDesconto(object.getPercentualDesconto())
                .setIdEmpresa(object.getIdEmpresa())
                .setUltimaAlteracao(object.getUltimaAlteracao())
                .setAtivo(object.isAtivo())
                .setCpfCnpjVendedor(object.getCpfCnpjVendedor())
                .setCnpjEmpresa(object.getCnpjEmpresa());
    }

    @Override public FormaPagamento toViewObject(final FormaPagamentoEntity entity) {
        Integer id = entity.getId();
        String codigo = entity.getCodigo();
        String descricao = entity.getDescricao();
        Float percentualDesconto = entity.getPercentualDesconto();
        Integer idEmpresa = entity.getIdEmpresa();
        String ultimaAlteracao = entity.getUltimaAlteracao();
        Boolean ativo = entity.isAtivo();
        String cpfCnpjVendedor = entity.getCpfCnpjVendedor();
        String cnpjEmpresa = entity.getCnpjEmpresa();

        return FormaPagamento.create(
                id, codigo, descricao, percentualDesconto, idEmpresa, ultimaAlteracao, ativo,
                cpfCnpjVendedor, cnpjEmpresa
        );
    }
}
