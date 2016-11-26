package br.com.libertsolutions.libertvendas.app.domain.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Filipe Bezerra
 */
public class ItemPedidoResponseDto {
    @SerializedName("idItem") @Expose int idItem;

    @SerializedName("ultimaAlteracao") @Expose String ultimaAlteracao;
}
