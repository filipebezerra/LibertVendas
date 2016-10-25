package br.com.libertsolutions.libertvendas.app.domain.entity;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * @author Filipe Bezerra
 */

@RealmClass
public class ProdutoEntity implements RealmModel {
    private String nome;

    private Float quantidadeEstoque;

    private Double preco;

    public String getNome() {
        return nome;
    }

    public ProdutoEntity setNome(String pNome) {
        nome = pNome;
        return this;
    }

    public Float getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public ProdutoEntity setQuantidadeEstoque(Float pQuantidadeEstoque) {
        quantidadeEstoque = pQuantidadeEstoque;
        return this;
    }

    public Double getPreco() {
        return preco;
    }

    public ProdutoEntity setPreco(Double pPreco) {
        preco = pPreco;
        return this;
    }
}
