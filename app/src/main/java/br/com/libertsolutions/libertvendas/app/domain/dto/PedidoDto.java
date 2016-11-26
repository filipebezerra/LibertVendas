package br.com.libertsolutions.libertvendas.app.domain.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class PedidoDto {
    @SerializedName("tipo") @Expose public int tipo;

    @SerializedName("dtEmissao") @Expose public String dtEmissao;

    @SerializedName("desconto") @Expose public double desconto;

    @SerializedName("perDesc") @Expose public float perDesc;

    @SerializedName("observacao") @Expose public String observacao;

    @SerializedName("idCliente") @Expose public int idCliente;

    @SerializedName("idFormPgto") @Expose public int idFormPgto;

    @SerializedName("idTabela") @Expose public int idTabela;

    @SerializedName("Itens") @Expose public List<ItemPedidoDto> itens;

    public PedidoDto(int pTipo, String pDtEmissao, double pDesconto, float pPerDesc,
            String pObservacao, int pIdCliente, int pIdFormPgto, int pIdTabela,
            List<ItemPedidoDto> pItens) {
        tipo = pTipo;
        dtEmissao = pDtEmissao;
        desconto = pDesconto;
        perDesc = pPerDesc;
        observacao = pObservacao;
        idCliente = pIdCliente;
        idFormPgto = pIdFormPgto;
        idTabela = pIdTabela;
        itens = pItens;
    }
}
