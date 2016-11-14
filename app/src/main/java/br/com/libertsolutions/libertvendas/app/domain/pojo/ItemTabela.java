package br.com.libertsolutions.libertvendas.app.domain.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Filipe Bezerra
 */
public class ItemTabela implements Parcelable {
    private final int idItemTabela;

    private final double precoVenda;

    private final int idProduto;

    private final String ultimaAlteracao;

    public ItemTabela(
            int pIdItemTabela, double pPrecoVenda, int pIdProduto, String pUltimaAlteracao) {
        idItemTabela = pIdItemTabela;
        precoVenda = pPrecoVenda;
        idProduto = pIdProduto;
        ultimaAlteracao = pUltimaAlteracao;
    }

    protected ItemTabela(Parcel in) {
        idItemTabela = in.readInt();
        precoVenda = in.readDouble();
        idProduto = in.readInt();
        ultimaAlteracao = in.readString();
    }

    public static final Creator<ItemTabela> CREATOR = new Creator<ItemTabela>() {
        @Override public ItemTabela createFromParcel(Parcel in) {
            return new ItemTabela(in);
        }

        @Override public ItemTabela[] newArray(int size) {
            return new ItemTabela[size];
        }
    };

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

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel pOut, int pFlags) {
        pOut.writeInt(getIdItemTabela());
        pOut.writeDouble(getPrecoVenda());
        pOut.writeInt(getIdProduto());
        pOut.writeString(getUltimaAlteracao());
    }
}
