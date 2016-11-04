package br.com.libertsolutions.libertvendas.app.domain.entity;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * @author Filipe Bezerra
 */

@RealmClass public class ProdutoEntity implements RealmModel {
    @PrimaryKey private Integer idProduto;

    private String codigo;

    private String codigoBarras;

    @Required private String descricao;

    private String unidadeMedida;

    private String grupo;

    private Double precoVenda;

    private Float quantidade;

    private String observacao;

    private Boolean ativo;

    private String ultimaAlteracao;

    public Integer getIdProduto() {
        return idProduto;
    }

    public ProdutoEntity setIdProduto(Integer pIdProduto) {
        idProduto = pIdProduto;
        return this;
    }

    public String getCodigo() {
        return codigo;
    }

    public ProdutoEntity setCodigo(String pCodigo) {
        codigo = pCodigo;
        return this;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public ProdutoEntity setCodigoBarras(String pCodigoBarras) {
        codigoBarras = pCodigoBarras;
        return this;
    }

    public String getDescricao() {
        return descricao;
    }

    public ProdutoEntity setDescricao(String pDescricao) {
        descricao = pDescricao;
        return this;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public ProdutoEntity setUnidadeMedida(String pUnidadeMedida) {
        unidadeMedida = pUnidadeMedida;
        return this;
    }

    public String getGrupo() {
        return grupo;
    }

    public ProdutoEntity setGrupo(String pGrupo) {
        grupo = pGrupo;
        return this;
    }

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public ProdutoEntity setPrecoVenda(Double pPrecoVenda) {
        precoVenda = pPrecoVenda;
        return this;
    }

    public Float getQuantidade() {
        return quantidade;
    }

    public ProdutoEntity setQuantidade(Float pQuantidade) {
        quantidade = pQuantidade;
        return this;
    }

    public String getObservacao() {
        return observacao;
    }

    public ProdutoEntity setObservacao(String pObservacao) {
        observacao = pObservacao;
        return this;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public ProdutoEntity setAtivo(Boolean pAtivo) {
        ativo = pAtivo;
        return this;
    }

    public String getUltimaAlteracao() {
        return ultimaAlteracao;
    }

    public ProdutoEntity setUltimaAlteracao(String pUltimaAlteracao) {
        ultimaAlteracao = pUltimaAlteracao;
        return this;
    }
}
