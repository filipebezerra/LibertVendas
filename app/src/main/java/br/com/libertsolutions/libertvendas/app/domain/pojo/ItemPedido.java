package br.com.libertsolutions.libertvendas.app.domain.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.UUID;

/**
 * @author Filipe Bezerra
 */
public final class ItemPedido implements Parcelable {

    private static final int SEM_ID_ITEM = 0;
    private static final String SEM_ALTERACAO = "";

    private final String id;

    private final int idItem;

    private final double precoVenda;

    private final float quantidade;

    private final double subTotal;

    private final Produto produto;

    private final String ultimaAlteracao;

    public static final Creator<ItemPedido> CREATOR = new Creator<ItemPedido>() {
        @Override public ItemPedido createFromParcel(Parcel in) {
            return new ItemPedido(in);
        }

        @Override public ItemPedido[] newArray(int size) {
            return new ItemPedido[size];
        }
    };

    public static ItemPedido createNew(
            final double precoVenda, final float quantidade, final double subTotal,
            final Produto produto) {

        return new ItemPedido(
                generateId(), SEM_ID_ITEM, precoVenda, quantidade, subTotal, produto, SEM_ALTERACAO);
    }

    private static String generateId() {
        return UUID.randomUUID().toString();
    }

    public static ItemPedido map(
            final String id, final int idItem, final double precoVenda, final float quantidade,
            final double subTotal, final Produto produto, final String ultimaAlteracao) {

        return new ItemPedido(
                id, idItem, precoVenda, quantidade, subTotal, produto, ultimaAlteracao);
    }

    private ItemPedido(Parcel in) {
        id = in.readString();
        idItem = in.readInt();
        precoVenda = in.readDouble();
        quantidade = in.readFloat();
        subTotal = in.readDouble();
        produto = in.readParcelable(Produto.class.getClassLoader());
        ultimaAlteracao = in.readString();
    }

    private ItemPedido(
            final String id, final int idItem, final double precoVenda, final float quantidade,
            final double subTotal, final Produto produto, final String ultimaAlteracao) {

        this.id = id;
        this.idItem = idItem;
        this.precoVenda = precoVenda;
        this.quantidade = quantidade;
        this.subTotal = subTotal;
        this.produto = produto;
        this.ultimaAlteracao = ultimaAlteracao;
    }

    public String getId() {
        return id;
    }

    public int getIdItem() {
        return idItem;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public float getQuantidade() {
        return quantidade;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public Produto getProduto() {
        return produto;
    }

    public String getUltimaAlteracao() {
        return ultimaAlteracao;
    }

    @Override public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ItemPedido that = (ItemPedido) o;

        return getId().equals(that.getId());
    }

    @Override public int hashCode() {
        return getId().hashCode();
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("ItemPedido{");
        sb.append("id='").append(id).append('\'');
        sb.append(", idItem=").append(idItem);
        sb.append(", precoVenda=").append(precoVenda);
        sb.append(", quantidade=").append(quantidade);
        sb.append(", subTotal=").append(subTotal);
        sb.append(", produto=").append(produto);
        sb.append(", ultimaAlteracao='").append(ultimaAlteracao).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(final Parcel out, final int flags) {
        out.writeString(id);
        out.writeInt(idItem);
        out.writeDouble(precoVenda);
        out.writeFloat(quantidade);
        out.writeDouble(subTotal);
        out.writeParcelable(produto, flags);
        out.writeString(ultimaAlteracao);
    }
}
