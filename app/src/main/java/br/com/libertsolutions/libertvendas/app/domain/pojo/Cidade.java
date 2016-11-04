package br.com.libertsolutions.libertvendas.app.domain.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Filipe Bezerra
 */
public class Cidade implements Parcelable {
    private final int idCidade;

    private final String codMunicipio;

    private final String nome;

    private final Estado estado;

    protected Cidade(Parcel in) {
        idCidade = in.readInt();
        codMunicipio = in.readString();
        nome = in.readString();
        estado = in.readParcelable(Estado.class.getClassLoader());
    }

    public static final Creator<Cidade> CREATOR = new Creator<Cidade>() {
        @Override public Cidade createFromParcel(Parcel in) {
            return new Cidade(in);
        }

        @Override public Cidade[] newArray(int size) {
            return new Cidade[size];
        }
    };

    public Cidade(int pIdCidade, String pCodMunicipio, String pNome, Estado pEstado) {
        idCidade = pIdCidade;
        codMunicipio = pCodMunicipio;
        nome = pNome;
        estado = pEstado;
    }

    public int getIdCidade() {
        return idCidade;
    }

    public String getCodMunicipio() {
        return codMunicipio;
    }

    public String getNome() {
        return nome;
    }

    public Estado getEstado() {
        return estado;
    }

    @Override public boolean equals(Object pAnotherCidade) {
        if (this == pAnotherCidade) {
            return true;
        }
        if (pAnotherCidade == null || getClass() != pAnotherCidade.getClass()) {
            return false;
        }

        Cidade cidade = (Cidade) pAnotherCidade;

        return getIdCidade() == cidade.getIdCidade();
    }

    @Override public int hashCode() {
        return getIdCidade();
    }

    @SuppressWarnings("StringBufferReplaceableByString") @Override public String toString() {
        final StringBuilder sb = new StringBuilder("Cidade{");
        sb.append("idCidade=").append(idCidade);
        sb.append(", codMunicipio='").append(codMunicipio).append('\'');
        sb.append(", nome='").append(nome).append('\'');
        sb.append(", estado=").append(estado);
        sb.append('}');
        return sb.toString();
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel pOut, int pFlags) {
        pOut.writeInt(idCidade);
        pOut.writeString(codMunicipio);
        pOut.writeString(nome);
        pOut.writeParcelable(estado, pFlags);
    }
}
