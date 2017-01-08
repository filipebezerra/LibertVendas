package br.com.libertsolutions.libertvendas.app.domain.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import java.util.UUID;

/**
 * @author Filipe Bezerra
 */
public final class Pedido implements Parcelable {

    private static final int SEM_ID_PEDIDO = 0;
    private static final String SEM_ALTERACAO = "";

    public static final int TIPO_NORMAL = 0;
    public static final int TIPO_DEVOLUCAO = 1;

    public static final int STATUS_PENDENTE = 1;
    public static final int STATUS_ENVIADO = 2;
    public static final int STATUS_CANCELADO = 3;

    private final String id;

    private final int idPedido;

    private final int tipo;

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
                generateId(), SEM_ID_PEDIDO, TIPO_NORMAL, STATUS_PENDENTE, dataEmissao,
                desconto, formaPagamento.getPercentualDesconto(), observacao, cliente,
                formaPagamento, tabela, itens, SEM_ALTERACAO, cnpjEmpresa, cpfCnpjVendedor);
    }

    private static String generateId() {
        return UUID.randomUUID().toString();
    }

    public static Pedido changed(
            Pedido pedidoEdited, final long dataEmissao, final double desconto,
            final String observacao, final Cliente cliente, final FormaPagamento formaPagamento,
            final List<ItemPedido> itens, final String ultimaAlteracao, final String cnpjEmpresa,
            final String cpfCnpjVendedor) {

        if (pedidoEdited.getStatus() == STATUS_ENVIADO) {
            throw new IllegalStateException(
                    "Pedido já enviado para servidor, não pode ser alterado");
        }

        return new Pedido(
                pedidoEdited.getId(), pedidoEdited.getIdPedido(), pedidoEdited.getTipo(),
                pedidoEdited.getStatus(), dataEmissao, desconto,
                formaPagamento.getPercentualDesconto(), observacao, cliente, formaPagamento,
                pedidoEdited.getTabela(), itens, ultimaAlteracao, cnpjEmpresa, cpfCnpjVendedor);
    }

    public static Pedido map(
            final String id, final int idPedido, final int tipo, final int status,
            final long dataEmissao, final double desconto, final float percentualDesconto,
            final String observacao, final Cliente cliente, final FormaPagamento formaPagamento,
            final Tabela tabela, final List<ItemPedido> itens, final String ultimaAlteracao,
            final String cnpjEmpresa, final String cpfCnpjVendedor) {

        return new Pedido(
                id, idPedido, tipo, status, dataEmissao, desconto, percentualDesconto,
                observacao, cliente, formaPagamento, tabela, itens, ultimaAlteracao, cnpjEmpresa,
                cpfCnpjVendedor);
    }

    private Pedido(Parcel in) {
        id = in.readString();
        idPedido = in.readInt();
        tipo = in.readInt();
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
            final String id, final int idPedido, final int tipo, final int status,
            final long dataEmissao, final double desconto, final float percentualDesconto,
            final String observacao, final Cliente cliente, final FormaPagamento formaPagamento,
            final Tabela tabela, final List<ItemPedido> itens, final String ultimaAlteracao,
            final String cnpjEmpresa, final String cpfCnpjVendedor) {

        this.id = id;
        this.idPedido = idPedido;
        this.tipo = tipo;
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

    public String getId() {
        return id;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public int getTipo() {
        return tipo;
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

    @Override public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Pedido pedido = (Pedido) o;

        return getId().equals(pedido.getId());
    }

    @Override public int hashCode() {
        return getId().hashCode();
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("Pedido{");
        sb.append("id='").append(id).append('\'');
        sb.append(", idPedido=").append(idPedido);
        sb.append(", tipo=").append(tipo);
        sb.append(", status=").append(status);
        sb.append(", dataEmissao=").append(dataEmissao);
        sb.append(", desconto=").append(desconto);
        sb.append(", percentualDesconto=").append(percentualDesconto);
        sb.append(", observacao='").append(observacao).append('\'');
        sb.append(", cliente=").append(cliente);
        sb.append(", formaPagamento=").append(formaPagamento);
        sb.append(", tabela=").append(tabela);
        sb.append(", itens=").append(itens);
        sb.append(", ultimaAlteracao='").append(ultimaAlteracao).append('\'');
        sb.append(", cnpjEmpresa='").append(cnpjEmpresa).append('\'');
        sb.append(", cpfCnpjVendedor='").append(cpfCnpjVendedor).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(final Parcel out, final int flags) {
        out.writeString(id);
        out.writeInt(idPedido);
        out.writeInt(tipo);
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
