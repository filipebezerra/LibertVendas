package br.com.libertsolutions.libertvendas.app.domain.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Filipe Bezerra
 */
public final class VendedorDto {
    @SerializedName("idVendedor") @Expose public int idVendedor;

    @SerializedName("codigo") @Expose public String codigo;

    @SerializedName("nome") @Expose public String nome;

    @SerializedName("cpfCnpj") @Expose public String cpfCnpj;

    @SerializedName("telefone") @Expose public String telefone;

    @SerializedName("email") @Expose public String email;

    @SerializedName("ativo") @Expose public boolean ativo;

    @SerializedName("idTabela") @Expose public int idTabela;

    @SerializedName("ultimaAlteracao") @Expose public String ultimaAlteracao;
}
