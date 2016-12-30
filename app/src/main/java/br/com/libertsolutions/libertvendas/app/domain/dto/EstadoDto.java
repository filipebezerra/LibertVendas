package br.com.libertsolutions.libertvendas.app.domain.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Filipe Bezerra
 */
public class EstadoDto {

    @SerializedName("idEstado") @Expose public int idEstado;

    @SerializedName("uf") @Expose public String uf;

    @SerializedName("nome") @Expose public String nome;
}
