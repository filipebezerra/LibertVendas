package br.com.libertsolutions.libertvendas.app.domain.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class VendedorDto {

    @SerializedName("idVendedor") @Expose public int idVendedor;

    @SerializedName("codigo") @Expose public String codigo;

    @SerializedName("nome") @Expose public String nome;

    @SerializedName("cpfCnpj") @Expose public String cpfCnpj;

    @SerializedName("telefone") @Expose public String telefone;

    @SerializedName("email") @Expose public String email;

    @SerializedName("ativo") @Expose public boolean ativo;

    @SerializedName("ultimaAlteracao") @Expose public String ultimaAlteracao;

    @SerializedName("aplicaDesconto") @Expose public boolean aplicaDesconto;

    @SerializedName("Empresas") @Expose public List<EmpresaDto> empresas = new ArrayList<>();
}
