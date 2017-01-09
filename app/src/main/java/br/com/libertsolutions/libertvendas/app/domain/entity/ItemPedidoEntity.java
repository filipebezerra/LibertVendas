package br.com.libertsolutions.libertvendas.app.domain.entity;

import br.com.libertsolutions.libertvendas.app.data.repository.Entity;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * @author Filipe Bezerra
 */
@RealmClass public class ItemPedidoEntity implements Entity<String>, RealmModel {

    @PrimaryKey private String id;

    private Integer idItem;

    private Double precoVenda;

    private Float quantidade;

    private Double subTotal;

    private ProdutoEntity produto;

    private String ultimaAlteracao;

    @Override public ItemPedidoEntity setId(final String id) {
        this.id = id;
        return this;
    }

    @Override public String getId() {
        return id;
    }

    public Integer getIdItem() {
        return idItem;
    }

    public ItemPedidoEntity setIdItem(final Integer idItem) {
        this.idItem = idItem;
        return this;
    }

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public ItemPedidoEntity setPrecoVenda(final Double precoVenda) {
        this.precoVenda = precoVenda;
        return this;
    }

    public Float getQuantidade() {
        return quantidade;
    }

    public ItemPedidoEntity setQuantidade(final Float quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public ItemPedidoEntity setSubTotal(final Double subTotal) {
        this.subTotal = subTotal;
        return this;
    }

    public ProdutoEntity getProduto() {
        return produto;
    }

    public ItemPedidoEntity setProduto(
            final ProdutoEntity produto) {
        this.produto = produto;
        return this;
    }

    public String getUltimaAlteracao() {
        return ultimaAlteracao;
    }

    public ItemPedidoEntity setUltimaAlteracao(final String ultimaAlteracao) {
        this.ultimaAlteracao = ultimaAlteracao;
        return this;
    }
}
