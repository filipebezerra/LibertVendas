package br.com.libertsolutions.libertvendas.app.domain.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class PedidoResponseDto {
    @SerializedName("idPedido") @Expose public int idPedido;

    @SerializedName("numero") @Expose String numero;

    @SerializedName("status") @Expose int status;

    @SerializedName("ultimaAlteracao") @Expose String ultimaAlteracao;

    @SerializedName("Itens") @Expose List<ItemPedidoResponseDto> itens;
}
