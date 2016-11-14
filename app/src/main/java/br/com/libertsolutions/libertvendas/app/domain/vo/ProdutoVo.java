package br.com.libertsolutions.libertvendas.app.domain.vo;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Produto;

/**
 * @author Filipe Bezerra
 */
public class ProdutoVo {
    private final Produto produto;

    private double totalProdutos = 0;

    private float quantidadeAdicionada = 0;

    public ProdutoVo(Produto pProduto) {
        produto = pProduto;
    }

    public String getNome() {
        return produto.getDescricao();
    }

    public double getPreco() {
        return produto.getPrecoVenda();
    }

    public double getTotalProdutos() {
        return totalProdutos;
    }

    public float getQuantidadeAdicionada() {
        return quantidadeAdicionada;
    }

    public Produto getProduto() {
        return produto;
    }

    public synchronized void addQuantidade() {
        quantidadeAdicionada++;
        calcularTotalProdutos();
    }

    public synchronized void setQuantidade(float pQuantidade) {
        quantidadeAdicionada = pQuantidade;
        calcularTotalProdutos();
    }

    public synchronized boolean removeQuantidade() {
        if (quantidadeAdicionada > 0) {
            quantidadeAdicionada--;
            calcularTotalProdutos();
            return true;
        }
        return false;
    }

    private void calcularTotalProdutos() {
        totalProdutos = quantidadeAdicionada * produto.getPrecoVenda();
    }
}
