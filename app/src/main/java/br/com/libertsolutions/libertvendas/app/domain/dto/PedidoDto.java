package br.com.libertsolutions.libertvendas.app.domain.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class PedidoDto {

    @SerializedName("tipo") @Expose public int tipo;

    @SerializedName("numero") @Expose public String numero;

    @SerializedName("dtEmissao") @Expose public String dtEmissao;

    @SerializedName("desconto") @Expose public double desconto;

    @SerializedName("percDesc") @Expose public float percDesc;

    @SerializedName("observacao") @Expose public String observacao;

    @SerializedName("idCliente") @Expose public int idCliente;

    @SerializedName("idFormPgto") @Expose public int idFormPgto;

    @SerializedName("idTabela") @Expose public int idTabela;

    @SerializedName("Itens") @Expose public List<ItemPedidoDto> itens;
}
