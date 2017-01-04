package br.com.libertsolutions.libertvendas.app.domain.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Filipe Bezerra
 */
public final class Estado implements Parcelable {

    private final int idEstado;

    private final String uf;

    private final String nome;

    public static final Creator<Estado> CREATOR = new Creator<Estado>() {
        @Override public Estado createFromParcel(Parcel in) {
            return new Estado(in);
        }

        @Override public Estado[] newArray(int size) {
            return new Estado[size];
        }
    };

    public static Estado create(final int idEstado, final String uf, final String nome) {
        return new Estado(idEstado, uf, nome);
    }

    private Estado(Parcel in) {
        idEstado = in.readInt();
        uf = in.readString();
        nome = in.readString();
    }

    private Estado(final int idEstado, final String uf, final String nome) {
        this.idEstado = idEstado;
        this.uf = uf;
        this.nome = nome;
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

    @Override public boolean equals(Object anotherEstado) {
        if (this == anotherEstado) {
            return true;
        }
        if (anotherEstado == null || getClass() != anotherEstado.getClass()) {
            return false;
        }

        Estado estado = (Estado) anotherEstado;

        return getIdEstado() == estado.getIdEstado();
    }

    @Override public int hashCode() {
        return getIdEstado();
    }

    @Override public String toString() {
        return getNome();
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(final Parcel out, final int flags) {
        out.writeInt(idEstado);
        out.writeString(uf);
        out.writeString(nome);
    }
}
