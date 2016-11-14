package br.com.libertsolutions.libertvendas.app.domain.pojo;

import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class Pedido {
    private static final int SEM_ID = 0;
    private static final int SEM_ID_PEDIDO = 0;
    private static final String SEM_NUMERO = "";
    private static final String SEM_ALTERACAO = "";

    public static final int TIPO_NORMAL = 0;
    public static final int TIPO_DEVOLUCAO = 1;

    public static final int STATUS_EM_DIGITACAO = 0;
    public static final int STATUS_PENDENTE = 1;
    public static final int STATUS_ENVIADO = 2;
    public static final int STATUS_CANCELADO = 3;

    private final int id;

    private final int idPedido;

    private final int tipo;

    private final String numero;

    private final int status;

    private final long dataEmissao;

    private final double desconto;

    private final float percentualDesconto;

    private final String observacao;

    private final Cliente cliente;

    private final FormaPagamento formaPagamento;

    private final TabelaPreco tabelaPreco;

    private final List<ItemPedido> itens;

    private final String ultimaAlteracao;

    public Pedido(
            int pId, int pIdPedido, int pTipo, String pNumero, int pStatus, long pDataEmissao,
            double pDesconto, float pPercentualDesconto, String pObservacao, Cliente pCliente,
            FormaPagamento pFormaPagamento, TabelaPreco pTabelaPreco, List<ItemPedido> pItens,
            String pUltimaAlteracao) {
        id = pId;
        idPedido = pIdPedido;
        tipo = pTipo;
        numero = pNumero;
        status = pStatus;
        dataEmissao = pDataEmissao;
        desconto = pDesconto;
        percentualDesconto = pPercentualDesconto;
        observacao = pObservacao;
        cliente = pCliente;
        formaPagamento = pFormaPagamento;
        tabelaPreco = pTabelaPreco;
        itens = pItens;
        ultimaAlteracao = pUltimaAlteracao;
    }

    public static Pedido novoPedido(
            long pDataEmissao,
            double pDesconto, float pPercentualDesconto, String pObservacao, Cliente pCliente,
            FormaPagamento pFormaPagamento, TabelaPreco pTabelaPreco, List<ItemPedido> pItens) {
        return new Pedido(
                SEM_ID, SEM_ID_PEDIDO, TIPO_NORMAL, SEM_NUMERO, STATUS_EM_DIGITACAO,
                pDataEmissao, pDesconto, pPercentualDesconto, pObservacao, pCliente,
                pFormaPagamento, pTabelaPreco, pItens, SEM_ALTERACAO
        );
    }

    public int getId() {
        return id;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public int getTipo() {
        return tipo;
    }

    public String getNumero() {
        return numero;
    }

    public int getStatus() {
        return status;
    }

    public long getDataEmissao() {
        return dataEmissao;
    }

    public double getDesconto() {
        return desconto;
    }

    public float getPercentualDesconto() {
        return percentualDesconto;
    }

    public String getObservacao() {
        return observacao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public TabelaPreco getTabelaPreco() {
        return tabelaPreco;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public String getUltimaAlteracao() {
        return ultimaAlteracao;
    }

    @Override public boolean equals(Object pAnotherPedido) {
        if (this == pAnotherPedido) {
            return true;
        }
        if (pAnotherPedido == null || getClass() != pAnotherPedido.getClass()) {
            return false;
        }

        Pedido pedido = (Pedido) pAnotherPedido;

        if (getId() != pedido.getId()) {
            return false;
        }
        return getDataEmissao() == pedido.getDataEmissao();
    }

    @Override public int hashCode() {
        int result = getId();
        result = 31 * result + (int) (getDataEmissao() ^ (getDataEmissao() >>> 32));
        return result;
    }
}
