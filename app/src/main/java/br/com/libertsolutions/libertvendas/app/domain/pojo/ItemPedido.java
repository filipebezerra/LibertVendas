package br.com.libertsolutions.libertvendas.app.domain.pojo;

/**
 * @author Filipe Bezerra
 */
public class ItemPedido {
    private static final int SEM_ID = 0;
    private static final int SEM_ID_ITEM = 0;
    private static final double SEM_DESCONTO = 0;
    private static final String SEM_OBSERVACAO = "";
    private static final String SEM_ALTERACAO = "";

    private final int id;

    private final int idItem;

    private final double precoVenda;

    private final float quantidade;

    private final double desconto;

    private final double subTotal;

    private final String observacao;

    private final Produto produto;

    private final String ultimaAlteracao;

    public ItemPedido(
            int pId, int pIdItem, double pPrecoVenda, float pQuantidade, double pDesconto,
            double pSubTotal, String pObservacao, Produto pProduto, String pUltimaAlteracao) {
        id = pId;
        idItem = pIdItem;
        precoVenda = pPrecoVenda;
        quantidade = pQuantidade;
        desconto = pDesconto;
        subTotal = pSubTotal;
        observacao = pObservacao;
        produto = pProduto;
        ultimaAlteracao = pUltimaAlteracao;
    }

    public static ItemPedido novoItem(
            double pPrecoVenda, float pQuantidade, double pSubTotal, Produto pProduto) {
        return new ItemPedido(SEM_ID, SEM_ID_ITEM, pPrecoVenda, pQuantidade, SEM_DESCONTO,
                pSubTotal, SEM_OBSERVACAO, pProduto, SEM_ALTERACAO);
    }

    public int getId() {
        return id;
    }

    public int getIdItem() {
        return idItem;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public float getQuantidade() {
        return quantidade;
    }

    public double getDesconto() {
        return desconto;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public String getObservacao() {
        return observacao;
    }

    public Produto getProduto() {
        return produto;
    }

    public String getUltimaAlteracao() {
        return ultimaAlteracao;
    }

    @Override public boolean equals(Object pAnotherItemPedido) {
        if (this == pAnotherItemPedido) {
            return true;
        }
        if (pAnotherItemPedido == null || getClass() != pAnotherItemPedido.getClass()) {
            return false;
        }

        ItemPedido that = (ItemPedido) pAnotherItemPedido;

        return getId() == that.getId();
    }

    @Override public int hashCode() {
        return getId();
    }
}
