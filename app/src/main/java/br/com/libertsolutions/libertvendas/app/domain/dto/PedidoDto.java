package br.com.libertsolutions.libertvendas.app.domain.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class PedidoDto {

    @SerializedName("appKey") @Expose public String appKey;

    @SerializedName("tipo") @Expose public int tipo;

    @SerializedName("dtEmissao") @Expose public String dtEmissao;

    @SerializedName("desconto") @Expose public double desconto;

    @SerializedName("percDesc") @Expose public float percDesc;

    @SerializedName("observacao") @Expose public String observacao;

    @SerializedName("idCliente") @Expose public int idCliente;

    @SerializedName("idFormPgto") @Expose public int idFormPgto;

    @SerializedName("idTabela") @Expose public int idTabela;

    @SerializedName("status") @Expose public int status;

    @SerializedName("Itens") @Expose public List<ItemPedidoDto> itens = new ArrayList<>();

    @SerializedName("idPedido") @Expose public int idPedido;

    @SerializedName("ultimaAlteracao") @Expose public String ultimaAlteracao;
}
