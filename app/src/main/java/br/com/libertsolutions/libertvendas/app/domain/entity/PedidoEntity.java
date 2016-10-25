package br.com.libertsolutions.libertvendas.app.domain.entity;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * @author Filipe Bezerra
 */
@RealmClass
public class PedidoEntity implements RealmModel {
    private int numero;

    private ClienteEntity cliente;

    private double total;

    private long dataEmissao;

    private int status;

    public int getNumero() {
        return numero;
    }

    public PedidoEntity setNumero(int pNumero) {
        numero = pNumero;
        return this;
    }

    public ClienteEntity getCliente() {
        return cliente;
    }

    public PedidoEntity setCliente(ClienteEntity pCliente) {
        cliente = pCliente;
        return this;
    }

    public double getTotal() {
        return total;
    }

    public PedidoEntity setTotal(double pTotal) {
        total = pTotal;
        return this;
    }

    public long getDataEmissao() {
        return dataEmissao;
    }

    public PedidoEntity setDataEmissao(long pDataEmissao) {
        dataEmissao = pDataEmissao;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public PedidoEntity setStatus(int pStatus) {
        status = pStatus;
        return this;
    }
}
