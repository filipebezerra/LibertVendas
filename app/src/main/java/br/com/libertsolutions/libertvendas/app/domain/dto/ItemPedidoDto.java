package br.com.libertsolutions.libertvendas.app.domain.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Filipe Bezerra
 */
public class ItemPedidoDto {
    @SerializedName("item") @Expose public int item;

    @SerializedName("prcVenda") @Expose public double prcVenda;

    @SerializedName("subTotal") @Expose public double subTotal;

    @SerializedName("desconto") @Expose public double desconto;

    @SerializedName("quantidade") @Expose public float quantidade;

    @SerializedName("observacao") @Expose public String observacao;

    @SerializedName("idProduto") @Expose public int idProduto;

    public ItemPedidoDto(int pItem, double pPrcVenda, double pSubTotal, double pDesconto,
            float pQuantidade, String pObservacao, int pIdProduto) {
        item = pItem;
        prcVenda = pPrcVenda;
        subTotal = pSubTotal;
        desconto = pDesconto;
        quantidade = pQuantidade;
        observacao = pObservacao;
        idProduto = pIdProduto;
    }
}
