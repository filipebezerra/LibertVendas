package br.com.libertsolutions.libertvendas.app.domain.pojo;

/**
 * @author Filipe Bezerra
 */
public class Pedido {
    public static final int PEDIDO_STATUS_ORCAMENTO = 0;
    public static final int PEDIDO_STATUS_ENVIADO = 1;
    public static final int PEDIDO_STATUS_FATURADO = 2;
    public static final int PEDIDO_STATUS_CANCELADO = 3;

    private final int numero;

    private final Cliente cliente;

    private final double total;

    private final long dataEmissao;

    private final int status;

    public Pedido(int pNumero, Cliente pCliente, double pTotal, long pDataEmissao, int pStatus) {
        numero = pNumero;
        cliente = pCliente;
        total = pTotal;
        dataEmissao = pDataEmissao;
        status = pStatus;
    }

    public int getNumero() {
        return numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public double getTotal() {
        return total;
    }

    public long getDataEmissao() {
        return dataEmissao;
    }

    public int getStatus() {
        return status;
    }
}
