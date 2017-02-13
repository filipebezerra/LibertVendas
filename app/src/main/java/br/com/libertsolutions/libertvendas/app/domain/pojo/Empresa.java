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

    private final int idTabela;

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

    public static Empresa create(
            final int idEmpresa, final String nome, final String cnpj, final int idTabela) {
        return new Empresa(idEmpresa, nome, cnpj, idTabela);
    }

    private Empresa(Parcel in) {
        idEmpresa = in.readInt();
        nome = in.readString();
        cnpj = in.readString();
        idTabela = in.readInt();
    }

    private Empresa(int idEmpresa, String nome, String cnpj, int idTabela) {
        this.idEmpresa = idEmpresa;
        this.nome = nome;
        this.cnpj = cnpj;
        this.idTabela = idTabela;
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

    public int getIdTabela() {
        return idTabela;
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
        return getNome();
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(final Parcel out, final int flags) {
        out.writeInt(idEmpresa);
        out.writeString(nome);
        out.writeString(cnpj);
        out.writeInt(idTabela);
    }
}
