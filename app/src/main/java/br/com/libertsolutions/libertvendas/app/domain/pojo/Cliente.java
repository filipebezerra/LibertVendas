package br.com.libertsolutions.libertvendas.app.domain.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Filipe Bezerra
 */
public class Cliente implements Parcelable {
    private static final int SEM_ID = 0;

    private static final String SEM_CODIGO = "";

    private final long id;

    private final String codigo;

    private final String nome;

    private final int tipo;

    private final String cpfOuCnpj;

    private final String email;

    private final String telefone;

    private final String telefone2;

    private final String endereco;

    private final String cep;

    private final String bairro;

    private final String numero;

    private final String complemento;

    private final String cidade;

    private final String uf;

    public static final Creator<Cliente> CREATOR = new Creator<Cliente>() {
        @Override
        public Cliente createFromParcel(Parcel in) {
            return new Cliente(in);
        }

        @Override
        public Cliente[] newArray(int size) {
            return new Cliente[size];
        }
    };

    protected Cliente(Parcel in) {
        id = in.readLong();
        codigo = in.readString();
        nome = in.readString();
        tipo = in.readInt();
        cpfOuCnpj = in.readString();
        email = in.readString();
        telefone = in.readString();
        telefone2 = in.readString();
        endereco = in.readString();
        cep = in.readString();
        bairro = in.readString();
        numero = in.readString();
        complemento = in.readString();
        cidade = in.readString();
        uf = in.readString();
    }

    public static Cliente newCliente(
            String pNome, int pTipo, String pCpfOuCnpj, String pEmail,
            String pTelefone, String pTelefone2, String pEndereco, String pCep,
            String pBairro, String pNumero, String pComplemento, String pCidade, String pUf) {
        return new Cliente(
                SEM_ID, SEM_CODIGO, pNome, pTipo, pCpfOuCnpj, pEmail, pTelefone,
                pTelefone2, pEndereco, pCep, pBairro, pNumero, pComplemento, pCidade, pUf);
    }

    public static Cliente existingCliente(
            long pId, String pCodigo, String pNome, int pTipo, String pCpfOuCnpj,
            String pEmail, String pTelefone, String pTelefone2, String pEndereco,
            String pCep, String pBairro, String pNumero, String pComplemento,
            String pCidade, String pUf) {
        return new Cliente(
                pId, pCodigo, pNome, pTipo, pCpfOuCnpj, pEmail, pTelefone,
                pTelefone2, pEndereco, pCep, pBairro, pNumero, pComplemento, pCidade, pUf);
    }

    private Cliente(
            long pId, String pCodigo, String pNome, int pTipo, String pCpfOuCnpj,
            String pEmail, String pTelefone, String pTelefone2, String pEndereco,
            String pCep, String pBairro, String pNumero, String pComplemento,
            String pCidade, String pUf) {
        id = pId;
        codigo = pCodigo;
        nome = pNome;
        tipo = pTipo;
        cpfOuCnpj = pCpfOuCnpj;
        email = pEmail;
        telefone = pTelefone;
        telefone2 = pTelefone2;
        endereco = pEndereco;
        cep = pCep;
        bairro = pBairro;
        numero = pNumero;
        complemento = pComplemento;
        cidade = pCidade;
        uf = pUf;
    }

    public long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public int getTipo() {
        return tipo;
    }

    public String getCpfOuCnpj() {
        return cpfOuCnpj;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getCep() {
        return cep;
    }

    public String getBairro() {
        return bairro;
    }

    public String getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public String getUf() {
        return uf;
    }

    @Override
    public boolean equals(Object pO) {
        if (this == pO) {
            return true;
        }
        if (pO == null || getClass() != pO.getClass()) {
            return false;
        }

        Cliente cliente = (Cliente) pO;

        return getCpfOuCnpj().equals(cliente.getCpfOuCnpj());
    }

    @Override
    public int hashCode() {
        return getCpfOuCnpj().hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(codigo);
        dest.writeString(nome);
        dest.writeInt(tipo);
        dest.writeString(cpfOuCnpj);
        dest.writeString(email);
        dest.writeString(telefone);
        dest.writeString(telefone2);
        dest.writeString(endereco);
        dest.writeString(cep);
        dest.writeString(bairro);
        dest.writeString(numero);
        dest.writeString(complemento);
        dest.writeString(cidade);
        dest.writeString(uf);
    }
}
