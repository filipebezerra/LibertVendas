package br.com.libertsolutions.libertvendas.app.domain.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Filipe Bezerra
 */
public class OrderItemDto {

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("idProduto")
    @Expose
    public int productId;

    @SerializedName("quantidade")
    @Expose
    public float quantity;

    @SerializedName("subTotal")
    @Expose
    public double subTotal;

    @SerializedName("prcVenda")
    @Expose
    public double salesPrice;

    @SerializedName("idItem")
    @Expose
    public int orderItemId;

    @SerializedName("ultimaAlteracao")
    @Expose
    public String lastChangeTime;

    public OrderItemDto(
            final int id, final int productId, final float quantity,
            final double subTotal, final double salesPrice) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.subTotal = subTotal;
        this.salesPrice = salesPrice;
    }
}
