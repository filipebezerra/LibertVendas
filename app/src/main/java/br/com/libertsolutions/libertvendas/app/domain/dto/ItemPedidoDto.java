package br.com.libertsolutions.libertvendas.app.domain.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Filipe Bezerra
 */
public class ItemPedidoDto {

    @SerializedName("appKey") @Expose public String appKey;

    @SerializedName("prcVenda") @Expose public double prcVenda;

    @SerializedName("subTotal") @Expose public double subTotal;

    @SerializedName("quantidade") @Expose public float quantidade;

    @SerializedName("idProduto") @Expose public int idProduto;

    @SerializedName("idItem") @Expose public int idItem;

    @SerializedName("ultimaAlteracao") @Expose public String ultimaAlteracao;
}
