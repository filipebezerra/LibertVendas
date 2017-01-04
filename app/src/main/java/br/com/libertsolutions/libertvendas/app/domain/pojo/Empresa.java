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

    private Empresa(final int idEmpresa, final String nome, final String cnpj) {
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

    @Override public boolean equals(Object pAnotherEmpresa) {
        if (this == pAnotherEmpresa) {
            return true;
        }
        if (pAnotherEmpresa == null || getClass() != pAnotherEmpresa.getClass()) {
            return false;
        }

        Empresa empresa = (Empresa) pAnotherEmpresa;

        if (getIdEmpresa() != empresa.getIdEmpresa()) {
            return false;
        }
        return getCnpj().equals(empresa.getCnpj());
    }

    @Override public int hashCode() {
        int result = getIdEmpresa();
        result = 31 * result + getCnpj().hashCode();
        return result;
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
    }
}
