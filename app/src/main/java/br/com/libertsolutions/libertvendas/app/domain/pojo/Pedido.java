package br.com.libertsolutions.libertvendas.app.domain.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public final class Pedido implements Parcelable {

    private static final int SEM_ID = 0;
    private static final int SEM_ID_PEDIDO = 0;
    private static final String SEM_NUMERO = "";
    private static final String SEM_ALTERACAO = "";

    public static final int TIPO_NORMAL = 0;
    public static final int TIPO_DEVOLUCAO = 1;

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

    private final Tabela tabela;

    private final List<ItemPedido> itens;

    private final String ultimaAlteracao;

    private final String cnpjEmpresa;

    private final String cpfCnpjVendedor;

    public static final Creator<Pedido> CREATOR = new Creator<Pedido>() {
        @Override public Pedido createFromParcel(Parcel in) {
            return new Pedido(in);
        }

        @Override public Pedido[] newArray(int size) {
            return new Pedido[size];
        }
    };

    public static Pedido createNew(
            final long dataEmissao, final double desconto, final FormaPagamento formaPagamento,
            final String observacao, final Cliente cliente, final Tabela tabela,
            final List<ItemPedido> itens, final String cnpjEmpresa, final String cpfCnpjVendedor) {
        return new Pedido(
                SEM_ID, SEM_ID_PEDIDO, TIPO_NORMAL, SEM_NUMERO, STATUS_PENDENTE, dataEmissao,
                desconto, formaPagamento.getPercentualDesconto(), observacao, cliente,
                formaPagamento, tabela, itens, SEM_ALTERACAO, cnpjEmpresa, cpfCnpjVendedor);
    }

    public static Pedido editing(
            Pedido pedidoEdited, final long dataEmissao, final double desconto,
            final String observacao, final Cliente cliente, final FormaPagamento formaPagamento,
            final List<ItemPedido> itens, final String ultimaAlteracao) {
        return new Pedido(
                pedidoEdited.getId(), pedidoEdited.getIdPedido(), pedidoEdited.getTipo(),
                pedidoEdited.getNumero(), pedidoEdited.getStatus(), dataEmissao, desconto,
                formaPagamento.getPercentualDesconto(), observacao, cliente, formaPagamento,
                pedidoEdited.getTabela(), itens, ultimaAlteracao, pedidoEdited.getCnpjEmpresa(),
                pedidoEdited.getCpfCnpjVendedor());
    }

    public static Pedido map(
            final int id, final int idPedido, final int tipo, final String numero, final int status,
            final long dataEmissao, final double desconto, final float percentualDesconto,
            final String observacao, final Cliente cliente, final FormaPagamento formaPagamento,
            final Tabela tabela, final List<ItemPedido> itens, final String ultimaAlteracao,
            final String cnpjEmpresa, final String cpfCnpjVendedor) {
        return new Pedido(
                id, idPedido, tipo, numero, status, dataEmissao, desconto, percentualDesconto,
                observacao, cliente, formaPagamento, tabela, itens, ultimaAlteracao, cnpjEmpresa,
                cpfCnpjVendedor);
    }

    private Pedido(Parcel in) {
        id = in.readInt();
        idPedido = in.readInt();
        tipo = in.readInt();
        numero = in.readString();
        status = in.readInt();
        dataEmissao = in.readLong();
        desconto = in.readDouble();
        percentualDesconto = in.readFloat();
        observacao = in.readString();
        cliente = in.readParcelable(Cliente.class.getClassLoader());
        formaPagamento = in.readParcelable(FormaPagamento.class.getClassLoader());
        tabela = in.readParcelable(Tabela.class.getClassLoader());
        itens = in.createTypedArrayList(ItemPedido.CREATOR);
        ultimaAlteracao = in.readString();
        cnpjEmpresa = in.readString();
        cpfCnpjVendedor = in.readString();
    }

    private Pedido(
            final int id, final int idPedido, final int tipo, final String numero, final int status,
            final long dataEmissao, final double desconto, final float percentualDesconto,
            final String observacao, final Cliente cliente, final FormaPagamento formaPagamento,
            final Tabela tabela, final List<ItemPedido> itens, final String ultimaAlteracao,
            final String cnpjEmpresa, final String cpfCnpjVendedor) {
        this.id = id;
        this.idPedido = idPedido;
        this.tipo = tipo;
        this.numero = numero;
        this.status = status;
        this.dataEmissao = dataEmissao;
        this.desconto = desconto;
        this.percentualDesconto = percentualDesconto;
        this.observacao = observacao;
        this.cliente = cliente;
        this.formaPagamento = formaPagamento;
        this.tabela = tabela;
        this.itens = itens;
        this.ultimaAlteracao = ultimaAlteracao;
        this.cnpjEmpresa = cnpjEmpresa;
        this.cpfCnpjVendedor = cpfCnpjVendedor;
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

    public Tabela getTabela() {
        return tabela;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public String getUltimaAlteracao() {
        return ultimaAlteracao;
    }

    public String getCnpjEmpresa() {
        return cnpjEmpresa;
    }

    public String getCpfCnpjVendedor() {
        return cpfCnpjVendedor;
    }

    @Override public boolean equals(final Object anotherPedido) {
        if (this == anotherPedido) {
            return true;
        }
        if (anotherPedido == null || getClass() != anotherPedido.getClass()) {
            return false;
        }

        Pedido pedido = (Pedido) anotherPedido;

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

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(final Parcel out, final int flags) {
        out.writeInt(id);
        out.writeInt(idPedido);
        out.writeInt(tipo);
        out.writeString(numero);
        out.writeInt(status);
        out.writeLong(dataEmissao);
        out.writeDouble(desconto);
        out.writeFloat(percentualDesconto);
        out.writeString(observacao);
        out.writeParcelable(cliente, flags);
        out.writeParcelable(formaPagamento, flags);
        out.writeParcelable(tabela, flags);
        out.writeTypedList(itens);
        out.writeString(ultimaAlteracao);
        out.writeString(cnpjEmpresa);
        out.writeString(cpfCnpjVendedor);
    }
}
