package br.com.libertsolutions.libertvendas.app.domain.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Filipe Bezerra
 */
public class ClienteDto {
    @SerializedName("idCliente") @Expose public int idCliente;

    @SerializedName("codigo") @Expose public String codigo;

    @SerializedName("nome") @Expose public String nome;

    @SerializedName("tipo") @Expose public int tipo;

    @SerializedName("cpfCnpj") @Expose public String cpfCnpj;

    @SerializedName("contato") @Expose public String contato;

    @SerializedName("email") @Expose public String email;

    @SerializedName("telefone") @Expose public String telefone;

    @SerializedName("telefone2") @Expose public String telefone2;

    @SerializedName("endereco") @Expose public String endereco;

    @SerializedName("cep") @Expose public String cep;

    @SerializedName("Cidade") @Expose public CidadeDto cidade;

    @SerializedName("bairro") @Expose public String bairro;

    @SerializedName("numero") @Expose public String numero;

    @SerializedName("complemento") @Expose public String complemento;

    @SerializedName("ultimaAlteracao") @Expose public String ultimaAlteracao;

    @SerializedName("ativo") @Expose public boolean ativo;
}
