package br.com.libertsolutions.libertvendas.app.domain.entity;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * @author Filipe Bezerra
 */
@RealmClass public class FormaPagamentoEntity implements RealmModel {
    @PrimaryKey
    private Integer idFormaPagamento;

    private String codigo;

    @Required
    private String descricao;

    private Float percentualDesconto;

    private Integer idEmpresa;

    private String ultimaAlteracao;

    private Boolean ativo;

    public Integer getIdFormaPagamento() {
        return idFormaPagamento;
    }

    public FormaPagamentoEntity setIdFormaPagamento(Integer pIdFormaPagamento) {
        idFormaPagamento = pIdFormaPagamento;
        return this;
    }

    public String getCodigo() {
        return codigo;
    }

    public FormaPagamentoEntity setCodigo(String pCodigo) {
        codigo = pCodigo;
        return this;
    }

    public String getDescricao() {
        return descricao;
    }

    public FormaPagamentoEntity setDescricao(String pDescricao) {
        descricao = pDescricao;
        return this;
    }

    public Float getPercentualDesconto() {
        return percentualDesconto;
    }

    public FormaPagamentoEntity setPercentualDesconto(Float pPercentualDesconto) {
        percentualDesconto = pPercentualDesconto;
        return this;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public FormaPagamentoEntity setIdEmpresa(Integer pIdEmpresa) {
        idEmpresa = pIdEmpresa;
        return this;
    }

    public String getUltimaAlteracao() {
        return ultimaAlteracao;
    }

    public FormaPagamentoEntity setUltimaAlteracao(String pUltimaAlteracao) {
        ultimaAlteracao = pUltimaAlteracao;
        return this;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public FormaPagamentoEntity setAtivo(Boolean pAtivo) {
        ativo = pAtivo;
        return this;
    }
}
