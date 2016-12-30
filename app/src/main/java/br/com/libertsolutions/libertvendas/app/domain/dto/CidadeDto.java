package br.com.libertsolutions.libertvendas.app.domain.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Filipe Bezerra
 */
public class CidadeDto {

    @SerializedName("idCidade") @Expose public int idCidade;

    @SerializedName("codMunicipio") @Expose public String codMunicipio;

    @SerializedName("nome") @Expose public String nome;

    @SerializedName("Estado") @Expose public EstadoDto estado;
}
