package br.com.libertsolutions.libertvendas.app.domain.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Filipe Bezerra
 */
public class Estado implements Parcelable {
    private final int idEstado;

    private final String uf;

    private final String nome;

    protected Estado(Parcel in) {
        idEstado = in.readInt();
        uf = in.readString();
        nome = in.readString();
    }

    public static final Creator<Estado> CREATOR = new Creator<Estado>() {
        @Override public Estado createFromParcel(Parcel in) {
            return new Estado(in);
        }

        @Override public Estado[] newArray(int size) {
            return new Estado[size];
        }
    };

    public Estado(int pIdEstado, String pUf, String pNome) {
        idEstado = pIdEstado;
        uf = pUf;
        nome = pNome;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public String getUf() {
        return uf;
    }

    public String getNome() {
        return nome;
    }

    @Override public boolean equals(Object pAnotherEstado) {
        if (this == pAnotherEstado) {
            return true;
        }
        if (pAnotherEstado == null || getClass() != pAnotherEstado.getClass()) {
            return false;
        }

        Estado estado = (Estado) pAnotherEstado;

        return getIdEstado() == estado.getIdEstado();
    }

    @Override public int hashCode() {
        return getIdEstado();
    }

    @SuppressWarnings("StringBufferReplaceableByString") @Override public String toString() {
        final StringBuilder sb = new StringBuilder("Estado{");
        sb.append("idEstado=").append(idEstado);
        sb.append(", uf='").append(uf).append('\'');
        sb.append(", nome='").append(nome).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel pOut, int pFlags) {
        pOut.writeInt(idEstado);
        pOut.writeString(uf);
        pOut.writeString(nome);
    }
}