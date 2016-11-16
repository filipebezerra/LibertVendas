package br.com.libertsolutions.libertvendas.app.domain.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Filipe Bezerra
 */
public class EmpresaDto {
    @SerializedName("idEmpresa") @Expose public int idEmpresa;

    @SerializedName("nome") @Expose public String nome;

    @SerializedName("cnpj") @Expose public String cnpj;
}
