package br.com.libertsolutions.libertvendas.app.domain.pojo;

/**
 * @author Filipe Bezerra
 */

public class Pedido {
    private final int numero;

    private final Cliente cliente;

    private final double total;

    private final long dataEmissao;

    public Pedido(int pNumero, Cliente pCliente, double pTotal, long pDataEmissao) {
        numero = pNumero;
        cliente = pCliente;
        total = pTotal;
        dataEmissao = pDataEmissao;
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
}
