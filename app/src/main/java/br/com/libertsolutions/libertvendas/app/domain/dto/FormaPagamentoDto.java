package br.com.libertsolutions.libertvendas.app.domain.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Filipe Bezerra
 */
public class FormaPagamentoDto {

    @SerializedName("idFormPgto") @Expose public int idFormPgto;

    @SerializedName("codigo") @Expose public String codigo;

    @SerializedName("descricao") @Expose public String descricao;

    @SerializedName("perDesc") @Expose public float perDesc;

    @SerializedName("id_empresa") @Expose public int idEmpresa;

    @SerializedName("ultimaAlteracao") @Expose public String ultimaAlteracao;

    @SerializedName("ativo") @Expose public boolean ativo;
}
