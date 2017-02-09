package br.com.libertsolutions.libertvendas.app.domain.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Filipe Bezerra
 */
public final class Cliente implements Parcelable {

    private static final int SEM_ID = 0;
    private static final String SEM_CODIGO = "";
    private static final boolean ATIVO = true;
    private static final String NAO_FOI_ALTERADO = "";
    private static final String SEM_CONTATO = "";

    public static final int STATUS_IMPORTADO = 1;
    public static final int STATUS_CRIADO = 2;
    public static final int STATUS_MODIFICADO = 3;
    public static final int STATUS_SINCRONIZADO = 4;

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

    private final String cpfCnpjVendedor;

    private final String cnpjEmpresa;

    private final int status;

    private final String nomeFantasia;

    public static final Creator<Cliente> CREATOR = new Creator<Cliente>() {
        @Override public Cliente createFromParcel(Parcel in) {
            return new Cliente(in);
        }

        @Override public Cliente[] newArray(int size) {
            return new Cliente[size];
        }
    };

    public static Cliente createNew(
            final String newNome,
            final int newTipo,
            final String newCfCnpj,
            final String newEmail,
            final String newTelefone,
            final String newTelefone2,
            final String newEndereco,
            final String newCep,
            final Cidade newCidade,
            final String newBairro,
            final String newNumero,
            final String newComplemento,
            final String cpfCnpjVendedor,
            final String cnpjEmpresa,
            final String nomeFantasia
    ) {
        return new Cliente(
                SEM_ID, SEM_ID,
                SEM_CODIGO,
                newNome,
                newTipo,
                newCfCnpj,
                SEM_CONTATO,
                newEmail,
                newTelefone,
                newTelefone2,
                newEndereco,
                newCep,
                newCidade,
                newBairro,
                newNumero,
                newComplemento,
                NAO_FOI_ALTERADO,
                ATIVO,
                cpfCnpjVendedor,
                cnpjEmpresa,
                STATUS_CRIADO,
                nomeFantasia
        );
    }

    public static Cliente changed(
            final Cliente clienteEdited,
            final String newNome,
            final int newTipo,
            final String newCfCnpj,
            final String newEmail,
            final String newTelefone,
            final String newTelefone2,
            final String newEndereco,
            final String newCep,
            final Cidade newCidade,
            final String newBairro,
            final String newNumero,
            final String newComplemento,
            final String ultimaAlteracao,
            final String cpfCnpjVendedor,
            final String cnpjEmpresa,
            final String nomeFantasia
    ) {
        return new Cliente(
                clienteEdited.getId(),
                clienteEdited.getIdCliente(),
                clienteEdited.getCodigo(),
                newNome,
                newTipo,
                newCfCnpj,
                clienteEdited.getContato(),
                newEmail,
                newTelefone,
                newTelefone2,
                newEndereco,
                newCep,
                newCidade,
                newBairro,
                newNumero,
                newComplemento,
                ultimaAlteracao,
                clienteEdited.isAtivo(),
                cpfCnpjVendedor,
                cnpjEmpresa,
                STATUS_MODIFICADO,
                nomeFantasia
        );
    }

    public static Cliente create(
            final int idCliente,
            final String codigo,
            final String nome,
            final int tipo,
            final String cpfCnpj,
            final String contato,
            final String email,
            final String telefone,
            final String telefone2,
            final String endereco,
            final String cep,
            final Cidade cidade,
            final String bairro,
            final String numero,
            final String complemento,
            final String ultimaAlteracao,
            final boolean ativo,
            final String cpfCnpjVendedor,
            final String cnpjEmpresa,
            final String nomeFantasia
    ) {
        return new Cliente(
                SEM_ID,
                idCliente,
                codigo,
                nome,
                tipo,
                cpfCnpj,
                contato,
                email,
                telefone,
                telefone2,
                endereco,
                cep,
                cidade,
                bairro,
                numero,
                complemento,
                ultimaAlteracao,
                ativo,
                cpfCnpjVendedor,
                cnpjEmpresa,
                STATUS_IMPORTADO,
                nomeFantasia
        );
    }

    public static Cliente map(
            final int id,
            final int idCliente,
            final String codigo,
            final String nome,
            final int tipo,
            final String cpfCnpj,
            final String contato,
            final String email,
            final String telefone,
            final String telefone2,
            final String endereco,
            final String cep,
            final Cidade cidade,
            final String bairro,
            final String numero,
            final String complemento,
            final String ultimaAlteracao,
            final boolean ativo,
            final String cnpjEmpresa,
            final String cpfCnpjVendedor,
            final int status,
            final String nomeFantasia
    ) {
        return new Cliente(
                id,
                idCliente,
                codigo,
                nome,
                tipo,
                cpfCnpj,
                contato,
                email,
                telefone,
                telefone2,
                endereco,
                cep,
                cidade,
                bairro,
                numero,
                complemento,
                ultimaAlteracao,
                ativo,
                cpfCnpjVendedor,
                cnpjEmpresa,
                status,
                nomeFantasia
        );
    }

    private Cliente(Parcel in) {
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
        cpfCnpjVendedor = in.readString();
        cnpjEmpresa = in.readString();
        status = in.readInt();
        nomeFantasia = in.readString();
    }

    private Cliente(
            final int id,
            final int idCliente,
            final String codigo,
            final String nome,
            final int tipo,
            final String cpfCnpj,
            final String contato,
            final String email,
            final String telefone,
            final String telefone2,
            final String endereco,
            final String cep,
            final Cidade cidade,
            final String bairro,
            final String numero,
            final String complemento,
            final String ultimaAlteracao,
            final boolean ativo,
            final String cpfCnpjVendedor,
            final String cnpjEmpresa,
            final int status,
            final String nomeFantasia
    ) {
        this.id = id;
        this.idCliente = idCliente;
        this.codigo = codigo;
        this.nome = nome;
        this.tipo = tipo;
        this.cpfCnpj = cpfCnpj;
        this.contato = contato;
        this.email = email;
        this.telefone = telefone;
        this.telefone2 = telefone2;
        this.endereco = endereco;
        this.cep = cep;
        this.cidade = cidade;
        this.bairro = bairro;
        this.numero = numero;
        this.complemento = complemento;
        this.ultimaAlteracao = ultimaAlteracao;
        this.ativo = ativo;
        this.cpfCnpjVendedor = cpfCnpjVendedor;
        this.cnpjEmpresa = cnpjEmpresa;
        this.status = status;
        this.nomeFantasia = nomeFantasia;
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

    public String getCpfCnpjVendedor() {
        return cpfCnpjVendedor;
    }

    public String getCnpjEmpresa() {
        return cnpjEmpresa;
    }

    public int getStatus() {
        return status;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    @Override public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Cliente cliente = (Cliente) o;

        if (getId() != cliente.getId()) {
            return false;
        }
        if (!getCnpjEmpresa().equals(cliente.getCnpjEmpresa())) {
            return false;
        }
        return getCpfCnpjVendedor().equals(cliente.getCpfCnpjVendedor());
    }

    @Override public int hashCode() {
        int result = getId();
        result = 31 * result + getCnpjEmpresa().hashCode();
        result = 31 * result + getCpfCnpjVendedor().hashCode();
        return result;
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("Cliente{");
        sb.append("id=").append(id);
        sb.append(", idCliente=").append(idCliente);
        sb.append(", codigo='").append(codigo).append('\'');
        sb.append(", nome='").append(nome).append('\'');
        sb.append(", tipo=").append(tipo);
        sb.append(", cpfCnpj='").append(cpfCnpj).append('\'');
        sb.append(", contato='").append(contato).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", telefone='").append(telefone).append('\'');
        sb.append(", telefone2='").append(telefone2).append('\'');
        sb.append(", endereco='").append(endereco).append('\'');
        sb.append(", cep='").append(cep).append('\'');
        sb.append(", cidade=").append(cidade);
        sb.append(", bairro='").append(bairro).append('\'');
        sb.append(", numero='").append(numero).append('\'');
        sb.append(", complemento='").append(complemento).append('\'');
        sb.append(", ultimaAlteracao='").append(ultimaAlteracao).append('\'');
        sb.append(", ativo=").append(ativo);
        sb.append(", cpfCnpjVendedor='").append(cpfCnpjVendedor).append('\'');
        sb.append(", cnpjEmpresa='").append(cnpjEmpresa).append('\'');
        sb.append(", status=").append(status);
        sb.append(", nomeFantasia=").append(nomeFantasia);
        sb.append('}');
        return sb.toString();
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(final Parcel out, final int flags) {
        out.writeInt(id);
        out.writeInt(idCliente);
        out.writeString(codigo);
        out.writeString(nome);
        out.writeInt(tipo);
        out.writeString(cpfCnpj);
        out.writeString(contato);
        out.writeString(email);
        out.writeString(telefone);
        out.writeString(telefone2);
        out.writeString(endereco);
        out.writeString(cep);
        out.writeParcelable(cidade, flags);
        out.writeString(bairro);
        out.writeString(numero);
        out.writeString(complemento);
        out.writeString(ultimaAlteracao);
        out.writeByte((byte) (ativo ? 1 : 0));
        out.writeString(cpfCnpjVendedor);
        out.writeString(cnpjEmpresa);
        out.writeInt(status);
        out.writeString(nomeFantasia);
    }
}
