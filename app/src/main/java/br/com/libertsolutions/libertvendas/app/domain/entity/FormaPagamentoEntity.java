package br.com.libertsolutions.libertvendas.app.domain.entity;

import br.com.libertsolutions.libertvendas.app.data.repository.Entity;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * @author Filipe Bezerra
 */
@RealmClass public class FormaPagamentoEntity implements Entity<Integer>, RealmModel {

    @PrimaryKey private Integer idFormaPagamento;

    private String codigo;

    @Required private String descricao;

    private Float percentualDesconto;

    private Integer idEmpresa;

    private String ultimaAlteracao;

    private Boolean ativo;

    private String cnpjEmpresa;

    private String cpfCnpjVendedor;

    @Override public FormaPagamentoEntity setId(final Integer id) {
        idFormaPagamento = id;
        return this;
    }

    @Override public Integer getId() {
        return idFormaPagamento;
    }

    public String getCodigo() {
        return codigo;
    }

    public FormaPagamentoEntity setCodigo(final String codigo) {
        this.codigo = codigo;
        return this;
    }

    public String getDescricao() {
        return descricao;
    }

    public FormaPagamentoEntity setDescricao(final String descricao) {
        this.descricao = descricao;
        return this;
    }

    public Float getPercentualDesconto() {
        return percentualDesconto;
    }

    public FormaPagamentoEntity setPercentualDesconto(final Float percentualDesconto) {
        this.percentualDesconto = percentualDesconto;
        return this;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public FormaPagamentoEntity setIdEmpresa(final Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
        return this;
    }

    public String getUltimaAlteracao() {
        return ultimaAlteracao;
    }

    public FormaPagamentoEntity setUltimaAlteracao(final String ultimaAlteracao) {
        this.ultimaAlteracao = ultimaAlteracao;
        return this;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public FormaPagamentoEntity setAtivo(final Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public String getCnpjEmpresa() {
        return cnpjEmpresa;
    }

    public FormaPagamentoEntity setCnpjEmpresa(final String cnpjEmpresa) {
        this.cnpjEmpresa = cnpjEmpresa;
        return this;
    }

    public String getCpfCnpjVendedor() {
        return cpfCnpjVendedor;
    }

    public FormaPagamentoEntity setCpfCnpjVendedor(final String cpfCnpjVendedor) {
        this.cpfCnpjVendedor = cpfCnpjVendedor;
        return this;
    }
}
