package br.com.libertsolutions.libertvendas.app.domain.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Filipe Bezerra
 */
public final class Empresa implements Parcelable {

    private final int idEmpresa;

    private final String nome;

    private final String cnpj;

    public static final Creator<Empresa> CREATOR = new Creator<Empresa>() {
        @Override
        public Empresa createFromParcel(Parcel in) {
            return new Empresa(in);
        }

        @Override
        public Empresa[] newArray(int size) {
            return new Empresa[size];
        }
    };

    public static Empresa create(final int idEmpresa, final String nome, final String cnpj) {
        return new Empresa(idEmpresa, nome, cnpj);
    }

    private Empresa(Parcel in) {
        idEmpresa = in.readInt();
        nome = in.readString();
        cnpj = in.readString();
    }

    private Empresa(int idEmpresa, String nome, String cnpj) {
        this.idEmpresa = idEmpresa;
        this.nome = nome;
        this.cnpj = cnpj;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public String getNome() {
        return nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    @Override public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Empresa empresa = (Empresa) o;

        return getIdEmpresa() == empresa.getIdEmpresa();
    }

    @Override public int hashCode() {
        return getIdEmpresa();
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("Empresa{");
        sb.append("idEmpresa=").append(idEmpresa);
        sb.append(", nome='").append(nome).append('\'');
        sb.append(", cnpj='").append(cnpj).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(final Parcel out, final int flags) {
        out.writeInt(idEmpresa);
        out.writeString(nome);
        out.writeString(cnpj);
    }
}
