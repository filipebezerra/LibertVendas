package br.com.libertsolutions.libertvendas.app.domain.pojo;

/**
 * @author Filipe Bezerra
 */

public class Produto {
    private final String nome;

    private final float quantidadeEstoque;

    private final double preco;

    public Produto(String pNome, float pQuantidadeEstoque, double pPreco) {
        nome = pNome;
        quantidadeEstoque = pQuantidadeEstoque;
        preco = pPreco;
    }

    public String getNome() {
        return nome;
    }

    public float getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public double getPreco() {
        return preco;
    }
}
