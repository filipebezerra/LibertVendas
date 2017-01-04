package br.com.libertsolutions.libertvendas.app.domain.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class VendedorResponseDto {

    @SerializedName("error") @Expose public boolean error;

    @SerializedName("mensagem") @Expose public String mensagem;

    @SerializedName("Vendedor") @Expose public VendedorDto vendedor;

    @SerializedName("Empresas") @Expose public List<EmpresaDto> empresas = new ArrayList<>();
}
