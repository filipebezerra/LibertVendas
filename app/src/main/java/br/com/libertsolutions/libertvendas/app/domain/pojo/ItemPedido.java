package br.com.libertsolutions.libertvendas.app.domain.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Filipe Bezerra
 */
public final class ItemPedido implements Parcelable {

    private static final int SEM_ID = 0;
    private static final int SEM_ID_ITEM = 0;
    private static final double SEM_DESCONTO = 0;
    private static final String SEM_OBSERVACAO = "";
    private static final String SEM_ALTERACAO = "";

    private final int id;

    private final int idItem;

    private final double precoVenda;

    private final float quantidade;

    private final double desconto;

    private final double subTotal;

    private final String observacao;

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
                SEM_ID, SEM_ID_ITEM, precoVenda, quantidade, SEM_DESCONTO, subTotal,
                SEM_OBSERVACAO, produto, SEM_ALTERACAO);
    }

    public static ItemPedido map(
            final int id, final int idItem, final double precoVenda, final float quantidade,
            final double desconto, final double subTotal, final String observacao,
            final Produto produto, final String ultimaAlteracao) {
        return new ItemPedido(
                id, idItem, precoVenda, quantidade, desconto, subTotal, observacao, produto,
                ultimaAlteracao);
    }

    private ItemPedido(Parcel in) {
        id = in.readInt();
        idItem = in.readInt();
        precoVenda = in.readDouble();
        quantidade = in.readFloat();
        desconto = in.readDouble();
        subTotal = in.readDouble();
        observacao = in.readString();
        produto = in.readParcelable(Produto.class.getClassLoader());
        ultimaAlteracao = in.readString();
    }

    private ItemPedido(
            final int id, final int idItem, final double precoVenda, final float quantidade,
            final double desconto, final double subTotal, final String observacao,
            final Produto produto, final String ultimaAlteracao) {
        this.id = id;
        this.idItem = idItem;
        this.precoVenda = precoVenda;
        this.quantidade = quantidade;
        this.desconto = desconto;
        this.subTotal = subTotal;
        this.observacao = observacao;
        this.produto = produto;
        this.ultimaAlteracao = ultimaAlteracao;
    }

    public int getId() {
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

    public double getDesconto() {
        return desconto;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public String getObservacao() {
        return observacao;
    }

    public Produto getProduto() {
        return produto;
    }

    public String getUltimaAlteracao() {
        return ultimaAlteracao;
    }

    @Override public boolean equals(final Object anotherItemPedido) {
        if (this == anotherItemPedido) {
            return true;
        }
        if (anotherItemPedido == null || getClass() != anotherItemPedido.getClass()) {
            return false;
        }

        ItemPedido that = (ItemPedido) anotherItemPedido;

        return getId() == that.getId();
    }

    @Override public int hashCode() {
        return getId();
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(final Parcel out, final int flags) {
        out.writeInt(id);
        out.writeInt(idItem);
        out.writeDouble(precoVenda);
        out.writeFloat(quantidade);
        out.writeDouble(desconto);
        out.writeDouble(subTotal);
        out.writeString(observacao);
        out.writeParcelable(produto, flags);
        out.writeString(ultimaAlteracao);
    }
}
