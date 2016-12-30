package br.com.libertsolutions.libertvendas.app.domain.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Filipe Bezerra
 */
public class ProdutoDto {

    @SerializedName("idProduto") @Expose public int idProduto;

    @SerializedName("codigo") @Expose public String codigo;

    @SerializedName("codigoBarras") @Expose public String codigoBarras;

    @SerializedName("descricao") @Expose public String descricao;

    @SerializedName("un") @Expose public String un;

    @SerializedName("grupo") @Expose public String grupo;

    @SerializedName("quantidade") @Expose public int quantidade;

    @SerializedName("obs") @Expose public String obs;

    @SerializedName("ativo") @Expose public boolean ativo;

    @SerializedName("ultimaAlteracao") @Expose public String ultimaAlteracao;
}
