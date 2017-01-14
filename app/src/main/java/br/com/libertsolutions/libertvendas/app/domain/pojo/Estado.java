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

    public static Estado create(
            final int idEstado,
            final String uf,
            final String nome
    ) {
        return new Estado(
                idEstado,
                uf,
                nome
        );
    }

    private Estado(Parcel in) {
        idEstado = in.readInt();
        uf = in.readString();
        nome = in.readString();
    }

    private Estado(
            final int idEstado,
            final String uf,
            final String nome
    ) {
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

    @Override public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Estado estado = (Estado) o;

        return getIdEstado() == estado.getIdEstado();
    }

    @Override public int hashCode() {
        return getIdEstado();
    }

    @Override public String toString() {
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

    @Override public void writeToParcel(final Parcel out, final int flags) {
        out.writeInt(idEstado);
        out.writeString(uf);
        out.writeString(nome);
    }
}
