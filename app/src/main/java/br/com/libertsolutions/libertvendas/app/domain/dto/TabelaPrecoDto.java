package br.com.libertsolutions.libertvendas.app.domain.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class TabelaPrecoDto {
    @SerializedName("idTabela") @Expose public int idTabela;

    @SerializedName("codigo") @Expose public String codigo;

    @SerializedName("nome") @Expose public String nome;

    @SerializedName("ativo") @Expose public boolean ativo;

    @SerializedName("ultimaAlteracao") @Expose public String ultimaAlteracao;

    @SerializedName("ItensTabela") @Expose public List<ItemTabelaDto> itensTabela = new ArrayList<>();
}
