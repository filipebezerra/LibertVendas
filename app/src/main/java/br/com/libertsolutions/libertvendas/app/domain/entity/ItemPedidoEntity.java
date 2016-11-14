package br.com.libertsolutions.libertvendas.app.domain.entity;

import br.com.libertsolutions.libertvendas.app.data.util.RealmAutoIncrement;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * @author Filipe Bezerra
 */
@RealmClass public class ItemPedidoEntity implements RealmModel {
    @PrimaryKey private Integer id
            = RealmAutoIncrement.getInstance(this.getClass()).getNextIdFromModel();

    private Integer idItem;

    private Double precoVenda;

    private Float quantidade;

    private Double desconto;

    private Double subTotal;

    private String observacao;

    private ProdutoEntity produto;

    private String ultimaAlteracao;

    public Integer getId() {
        return id;
    }

    public ItemPedidoEntity setId(Integer pId) {
        if (pId > 0) {
            id = pId;
        }
        return this;
    }

    public Integer getIdItem() {
        return idItem;
    }

    public ItemPedidoEntity setIdItem(Integer pIdItem) {
        idItem = pIdItem;
        return this;
    }

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public ItemPedidoEntity setPrecoVenda(Double pPrecoVenda) {
        precoVenda = pPrecoVenda;
        return this;
    }

    public Float getQuantidade() {
        return quantidade;
    }

    public ItemPedidoEntity setQuantidade(Float pQuantidade) {
        quantidade = pQuantidade;
        return this;
    }

    public Double getDesconto() {
        return desconto;
    }

    public ItemPedidoEntity setDesconto(Double pDesconto) {
        desconto = pDesconto;
        return this;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public ItemPedidoEntity setSubTotal(Double pSubTotal) {
        subTotal = pSubTotal;
        return this;
    }

    public String getObservacao() {
        return observacao;
    }

    public ItemPedidoEntity setObservacao(String pObservacao) {
        observacao = pObservacao;
        return this;
    }

    public ProdutoEntity getProduto() {
        return produto;
    }

    public ItemPedidoEntity setProduto(
            ProdutoEntity pProduto) {
        produto = pProduto;
        return this;
    }

    public String getUltimaAlteracao() {
        return ultimaAlteracao;
    }

    public ItemPedidoEntity setUltimaAlteracao(String pUltimaAlteracao) {
        ultimaAlteracao = pUltimaAlteracao;
        return this;
    }
}
