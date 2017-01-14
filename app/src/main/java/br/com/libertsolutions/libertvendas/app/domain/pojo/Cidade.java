package br.com.libertsolutions.libertvendas.app.domain.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Filipe Bezerra
 */
public final class Cidade implements Parcelable {

    private final int idCidade;

    private final String codMunicipio;

    private final String nome;

    private final Estado estado;

    public static final Creator<Cidade> CREATOR = new Creator<Cidade>() {
        @Override public Cidade createFromParcel(Parcel in) {
            return new Cidade(in);
        }

        @Override public Cidade[] newArray(int size) {
            return new Cidade[size];
        }
    };

    public static Cidade create(
            final int idCidade,
            final String codMunicipio,
            final String nome,
            final Estado estado
    ) {
        return new Cidade(
                idCidade,
                codMunicipio,
                nome,
                estado
        );
    }

    private Cidade(Parcel in) {
        idCidade = in.readInt();
        codMunicipio = in.readString();
        nome = in.readString();
        estado = in.readParcelable(Estado.class.getClassLoader());
    }

    private Cidade(
            final int idCidade,
            final String codMunicipio,
            final String nome,
            final Estado estado
    ) {
        this.idCidade = idCidade;
        this.codMunicipio = codMunicipio;
        this.nome = nome;
        this.estado = estado;
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

    @Override public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Cidade cidade = (Cidade) o;

        return getIdCidade() == cidade.getIdCidade();
    }

    @Override public int hashCode() {
        return getIdCidade();
    }

    @Override public String toString() {
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

    @Override public void writeToParcel(final Parcel out, final int flags) {
        out.writeInt(idCidade);
        out.writeString(codMunicipio);
        out.writeString(nome);
        out.writeParcelable(estado, flags);
    }
}
