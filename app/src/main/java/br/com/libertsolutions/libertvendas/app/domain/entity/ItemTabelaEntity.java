package br.com.libertsolutions.libertvendas.app.domain.entity;

import br.com.libertsolutions.libertvendas.app.data.repository.Entity;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * @author Filipe Bezerra
 */
@RealmClass public class ItemTabelaEntity implements Entity<Integer>, RealmModel {

    @PrimaryKey private Integer idItemTabela;

    @Required private Double precoVenda;

    @Required private Integer idProduto;

    private String ultimaAlteracao;

    private ProdutoEntity produto;

    @Override public ItemTabelaEntity setId(final Integer id) {
        idItemTabela = id;
        return this;
    }

    @Override public Integer getId() {
        return idItemTabela;
    }

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public ItemTabelaEntity setPrecoVenda(final Double precoVenda) {
        this.precoVenda = precoVenda;
        return this;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public ItemTabelaEntity setIdProduto(final Integer idProduto) {
        this.idProduto = idProduto;
        return this;
    }

    public String getUltimaAlteracao() {
        return ultimaAlteracao;
    }

    public ItemTabelaEntity setUltimaAlteracao(final String ultimaAlteracao) {
        this.ultimaAlteracao = ultimaAlteracao;
        return this;
    }

    public ProdutoEntity getProduto() {
        return produto;
    }

    public ItemTabelaEntity setProduto(final ProdutoEntity produto) {
        this.produto = produto;
        return this;
    }
}
