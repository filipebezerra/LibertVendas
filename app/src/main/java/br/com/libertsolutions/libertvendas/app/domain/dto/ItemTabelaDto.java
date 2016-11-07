package br.com.libertsolutions.libertvendas.app.domain.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Filipe Bezerra
 */
public class ItemTabelaDto {
    @SerializedName("idItensTabela") @Expose public int idItemTabela;

    @SerializedName("prcVenda") @Expose public double prcVenda;

    @SerializedName("idProduto") @Expose public int idProduto;

    @SerializedName("ultimaAlteracao") @Expose public String ultimaAlteracao;
}
