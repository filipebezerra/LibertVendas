package br.com.libertsolutions.libertvendas.app.domain.entity;

import br.com.libertsolutions.libertvendas.app.data.repository.Entity;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * @author Filipe Bezerra
 */
@RealmClass public class ProdutoEntity implements Entity<Integer>, RealmModel {

    @PrimaryKey private Integer idProduto;

    private String codigo;

    private String codigoBarras;

    @Required private String descricao;

    private String unidadeMedida;

    private String grupo;

    private Float quantidade;

    private String observacao;

    private Boolean ativo;

    private String ultimaAlteracao;

    @Override public ProdutoEntity setId(final Integer id) {
        idProduto = id;
        return this;
    }

    @Override public Integer getId() {
        return idProduto;
    }

    public String getCodigo() {
        return codigo;
    }

    public ProdutoEntity setCodigo(final String codigo) {
        this.codigo = codigo;
        return this;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public ProdutoEntity setCodigoBarras(final String codigoBarras) {
        this.codigoBarras = codigoBarras;
        return this;
    }

    public String getDescricao() {
        return descricao;
    }

    public ProdutoEntity setDescricao(final String descricao) {
        this.descricao = descricao;
        return this;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public ProdutoEntity setUnidadeMedida(final String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
        return this;
    }

    public String getGrupo() {
        return grupo;
    }

    public ProdutoEntity setGrupo(final String grupo) {
        this.grupo = grupo;
        return this;
    }

    public Float getQuantidade() {
        return quantidade;
    }

    public ProdutoEntity setQuantidade(final Float quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public String getObservacao() {
        return observacao;
    }

    public ProdutoEntity setObservacao(final String observacao) {
        this.observacao = observacao;
        return this;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public ProdutoEntity setAtivo(final Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public String getUltimaAlteracao() {
        return ultimaAlteracao;
    }

    public ProdutoEntity setUltimaAlteracao(final String ultimaAlteracao) {
        this.ultimaAlteracao = ultimaAlteracao;
        return this;
    }
}
