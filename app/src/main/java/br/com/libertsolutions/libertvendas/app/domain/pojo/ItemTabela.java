package br.com.libertsolutions.libertvendas.app.domain.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Filipe Bezerra
 */
public final class ItemTabela implements Parcelable {

    private final int idItemTabela;

    private final double precoVenda;

    private final int idProduto;

    private final String ultimaAlteracao;

    private final Produto produto;

    public static final Creator<ItemTabela> CREATOR = new Creator<ItemTabela>() {
        @Override public ItemTabela createFromParcel(Parcel in) {
            return new ItemTabela(in);
        }

        @Override public ItemTabela[] newArray(int size) {
            return new ItemTabela[size];
        }
    };

    public static ItemTabela create(
            final int idItemTabela,
            final double precoVenda,
            final int idProduto,
            final String ultimaAlteracao,
            final Produto produto
    ) {
        return new ItemTabela(
                idItemTabela,
                precoVenda,
                idProduto,
                ultimaAlteracao,
                produto
        );
    }

    private ItemTabela(Parcel in) {
        idItemTabela = in.readInt();
        precoVenda = in.readDouble();
        idProduto = in.readInt();
        ultimaAlteracao = in.readString();
        produto = in.readParcelable(Produto.class.getClassLoader());
    }

    private ItemTabela(
            final int idItemTabela,
            final double precoVenda,
            final int idProduto,
            final String ultimaAlteracao,
            final Produto produto
    ) {
        this.idItemTabela = idItemTabela;
        this.precoVenda = precoVenda;
        this.idProduto = idProduto;
        this.ultimaAlteracao = ultimaAlteracao;
        this.produto = produto;
    }

    public int getIdItemTabela() {
        return idItemTabela;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public String getUltimaAlteracao() {
        return ultimaAlteracao;
    }

    public Produto getProduto() {
        return produto;
    }

    @Override public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ItemTabela that = (ItemTabela) o;

        return getIdItemTabela() == that.getIdItemTabela();
    }

    @Override public int hashCode() {
        return getIdItemTabela();
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("ItemTabela{");
        sb.append("idItemTabela=").append(idItemTabela);
        sb.append(", precoVenda=").append(precoVenda);
        sb.append(", idProduto=").append(idProduto);
        sb.append(", ultimaAlteracao='").append(ultimaAlteracao).append('\'');
        sb.append(", produto=").append(produto);
        sb.append('}');
        return sb.toString();
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(final Parcel out, final int flags) {
        out.writeInt(idItemTabela);
        out.writeDouble(precoVenda);
        out.writeInt(idProduto);
        out.writeString(ultimaAlteracao);
        out.writeParcelable(produto, flags);
    }
}
