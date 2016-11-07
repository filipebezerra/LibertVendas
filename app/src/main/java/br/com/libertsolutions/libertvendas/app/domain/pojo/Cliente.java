package br.com.libertsolutions.libertvendas.app.domain.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Filipe Bezerra
 */
public class Cliente implements Parcelable {
    private static final int SEM_ID = 0;
    private static final String SEM_CODIGO = null;
    private static final boolean ATIVO = true;
    private static final String NAO_FOI_ALTERADO = null;

    private final int id;

    private final int idCliente;

    private final String codigo;

    private final String nome;

    private final int tipo;

    private final String cpfCnpj;

    private final String contato;

    private final String email;

    private final String telefone;

    private final String telefone2;

    private final String endereco;

    private final String cep;

    private final Cidade cidade;

    private final String bairro;

    private final String numero;

    private final String complemento;

    private final String ultimaAlteracao;

    private final boolean ativo;

    protected Cliente(Parcel in) {
        id = in.readInt();
        idCliente = in.readInt();
        codigo = in.readString();
        nome = in.readString();
        tipo = in.readInt();
        cpfCnpj = in.readString();
        contato = in.readString();
        email = in.readString();
        telefone = in.readString();
        telefone2 = in.readString();
        endereco = in.readString();
        cep = in.readString();
        cidade = in.readParcelable(Cidade.class.getClassLoader());
        bairro = in.readString();
        numero = in.readString();
        complemento = in.readString();
        ultimaAlteracao = in.readString();
        ativo = in.readByte() == 1;
    }

    public static final Creator<Cliente> CREATOR = new Creator<Cliente>() {
        @Override public Cliente createFromParcel(Parcel in) {
            return new Cliente(in);
        }

        @Override public Cliente[] newArray(int size) {
            return new Cliente[size];
        }
    };

    public Cliente(String pNome, int pTipo, String pCpfCnpj,
            String pContato, String pEmail, String pTelefone, String pTelefone2,
            String pEndereco, String pCep, Cidade pCidade, String pBairro, String pNumero,
            String pComplemento) {
        this(SEM_ID, SEM_CODIGO, pNome, pTipo, pCpfCnpj, pContato, pEmail,
                pTelefone, pTelefone2, pEndereco, pCep, pCidade, pBairro, pNumero,
                pComplemento, NAO_FOI_ALTERADO, ATIVO);
    }

    public Cliente(
            int pIdCliente, String pCodigo, String pNome, int pTipo, String pCpfCnpj,
            String pContato, String pEmail, String pTelefone, String pTelefone2,
            String pEndereco, String pCep, Cidade pCidade, String pBairro, String pNumero,
            String pComplemento, String pUltimaAlteracao, boolean pAtivo) {
        this(pIdCliente, pIdCliente, pCodigo, pNome, pTipo, pCpfCnpj, pContato, pEmail,
                pTelefone, pTelefone2, pEndereco, pCep, pCidade, pBairro, pNumero,
                pComplemento, pUltimaAlteracao, pAtivo);
    }

    public Cliente(
            int pId, int pIdCliente, String pCodigo, String pNome, int pTipo, String pCpfCnpj,
            String pContato, String pEmail, String pTelefone, String pTelefone2,
            String pEndereco, String pCep, Cidade pCidade, String pBairro, String pNumero,
            String pComplemento, String pUltimaAlteracao, boolean pAtivo) {
        id = pId;
        idCliente = pIdCliente;
        codigo = pCodigo;
        nome = pNome;
        tipo = pTipo;
        cpfCnpj = pCpfCnpj;
        contato = pContato;
        email = pEmail;
        telefone = pTelefone;
        telefone2 = pTelefone2;
        endereco = pEndereco;
        cep = pCep;
        cidade = pCidade;
        bairro = pBairro;
        numero = pNumero;
        complemento = pComplemento;
        ultimaAlteracao = pUltimaAlteracao;
        ativo = pAtivo;
    }

    public int getId() {
        return id;
    }

    public int getIdCliente() {
        return idCliente;
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

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public String getContato() {
        return contato;
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

    public Cidade getCidade() {
        return cidade;
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

    public String getUltimaAlteracao() {
        return ultimaAlteracao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    @Override public boolean equals(Object pAnotherCliente) {
        if (this == pAnotherCliente) {
            return true;
        }
        if (pAnotherCliente == null || getClass() != pAnotherCliente.getClass()) {
            return false;
        }

        Cliente cliente = (Cliente) pAnotherCliente;

        return getIdCliente() == cliente.getIdCliente();
    }

    @Override public int hashCode() {
        return getIdCliente();
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel pOut, int pFlags) {
        pOut.writeInt(id);
        pOut.writeInt(idCliente);
        pOut.writeString(codigo);
        pOut.writeString(nome);
        pOut.writeInt(tipo);
        pOut.writeString(cpfCnpj);
        pOut.writeString(contato);
        pOut.writeString(email);
        pOut.writeString(telefone);
        pOut.writeString(telefone2);
        pOut.writeString(endereco);
        pOut.writeString(cep);
        pOut.writeParcelable(cidade, pFlags);
        pOut.writeString(bairro);
        pOut.writeString(numero);
        pOut.writeString(complemento);
        pOut.writeString(ultimaAlteracao);
        pOut.writeByte((byte) (ativo ? 1 : 0));
    }
}
