package br.com.libertsolutions.libertvendas.app.domain.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class TabelaPreco implements Parcelable {
    private final int idTabela;

    private final String codigo;

    private final String nome;

    private final boolean ativo;

    private final String ultimaAlteracao;

    private final List<ItemTabela> itensTabela;

    public TabelaPreco(
            int pIdTabela, String pCodigo, String pNome, boolean pAtivo,
            String pUltimaAlteracao, List<ItemTabela> pItensTabela) {
        idTabela = pIdTabela;
        codigo = pCodigo;
        nome = pNome;
        ativo = pAtivo;
        ultimaAlteracao = pUltimaAlteracao;
        itensTabela = pItensTabela;
    }

    protected TabelaPreco(Parcel in) {
        idTabela = in.readInt();
        codigo = in.readString();
        nome = in.readString();
        ativo = in.readByte() == 1;
        ultimaAlteracao = in.readString();
        itensTabela = in.createTypedArrayList(ItemTabela.CREATOR);
    }

    public static final Creator<TabelaPreco> CREATOR = new Creator<TabelaPreco>() {
        @Override public TabelaPreco createFromParcel(Parcel in) {
            return new TabelaPreco(in);
        }

        @Override public TabelaPreco[] newArray(int size) {
            return new TabelaPreco[size];
        }
    };

    public int getIdTabela() {
        return idTabela;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public String getUltimaAlteracao() {
        return ultimaAlteracao;
    }

    public List<ItemTabela> getItensTabela() {
        return itensTabela;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel pOut, int pFlags) {
        pOut.writeInt(idTabela);
        pOut.writeString(codigo);
        pOut.writeString(nome);
        pOut.writeByte((byte) (ativo ? 1 : 0));
        pOut.writeString(ultimaAlteracao);
        pOut.writeTypedList(itensTabela);
    }
}
