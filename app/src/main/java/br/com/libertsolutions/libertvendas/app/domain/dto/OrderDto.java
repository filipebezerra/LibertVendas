package br.com.libertsolutions.libertvendas.app.domain.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class OrderDto {

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("tipo")
    @Expose
    public int type;

    @SerializedName("dtEmissao")
    @Expose
    public String issueDate;

    @SerializedName("desconto")
    @Expose
    public double discount;

    @SerializedName("perDesc")
    @Expose
    public float discountPercentage;

    @SerializedName("observacao")
    @Expose
    public String observation;

    @SerializedName("idCliente")
    @Expose
    public int customerId;

    @SerializedName("idFormPgto")
    @Expose
    public int paymentMethodId;

    @SerializedName("idTabela")
    @Expose
    public int priceTableId;

    @SerializedName("Itens")
    @Expose
    public List<OrderItemDto> items;

    @SerializedName("idPedido")
    @Expose
    public int orderId;

    @SerializedName("ultimaAlteracao")
    @Expose
    public String lastChangeTime;

    @SerializedName("status")
    @Expose
    public int status;

    public OrderDto(
            final int id,
            final int type,
            final String issueDate,
            final double discount,
            final float discountPercentage,
            final String observation,
            final int customerId,
            final int paymentMethodId,
            final int priceTableId,
            final List<OrderItemDto> items) {
        this.id = id;
        this.type = type;
        this.issueDate = issueDate;
        this.discount = discount;
        this.discountPercentage = discountPercentage;
        this.observation = observation;
        this.customerId = customerId;
        this.paymentMethodId = paymentMethodId;
        this.priceTableId = priceTableId;
        this.items = items;
    }
}
