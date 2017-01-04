package br.com.libertsolutions.libertvendas.app.domain.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public final class Vendedor implements Parcelable {

    private final int idVendedor;

    private final String codigo;

    private final String nome;

    private final String cpfCnpj;

    private final String telefone;

    private final String email;

    private final boolean ativo;

    private final int idTabela;

    private final String ultimaAlteracao;

    private final boolean aplicaDesconto;

    private final List<Empresa> empresas;

    private final Empresa empresaSelecionada;

    public static final Creator<Vendedor> CREATOR = new Creator<Vendedor>() {
        @Override public Vendedor createFromParcel(Parcel in) {
            return new Vendedor(in);
        }

        @Override public Vendedor[] newArray(int size) {
            return new Vendedor[size];
        }
    };

    public static Vendedor create(
            final int idVendedor, final String codigo, final String nome, final String cpfCnpj,
            final String telefone, final String email, final boolean ativo, final int idTabela,
            final String ultimaAlteracao, final boolean aplicaDesconto, List<Empresa> empresas,
            final Empresa empresaSelecionada) {
        return new Vendedor(idVendedor, codigo, nome, cpfCnpj, telefone, email, ativo, idTabela,
                ultimaAlteracao, aplicaDesconto, empresas, empresaSelecionada);
    }

    private Vendedor(Parcel in) {
        idVendedor = in.readInt();
        codigo = in.readString();
        nome = in.readString();
        cpfCnpj = in.readString();
        telefone = in.readString();
        email = in.readString();
        ativo = in.readByte() == 1;
        idTabela = in.readInt();
        ultimaAlteracao = in.readString();
        aplicaDesconto = in.readByte() == 1;
        empresas = in.createTypedArrayList(Empresa.CREATOR);
        empresaSelecionada = in.readParcelable(Empresa.class.getClassLoader());
    }

    private Vendedor(
            final int idVendedor, final String codigo, final String nome, final String cpfCnpj,
            final String telefone, final String email, final boolean ativo, final int idTabela,
            final String ultimaAlteracao, final boolean aplicaDesconto, List<Empresa> empresas,
            final Empresa empresaSelecionada) {
        this.idVendedor = idVendedor;
        this.codigo = codigo;
        this.nome = nome;
        this.cpfCnpj = cpfCnpj;
        this.telefone = telefone;
        this.email = email;
        this.ativo = ativo;
        this.idTabela = idTabela;
        this.ultimaAlteracao = ultimaAlteracao;
        this.aplicaDesconto = aplicaDesconto;
        this.empresas = empresas;
        this.empresaSelecionada = empresaSelecionada;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public int getIdTabela() {
        return idTabela;
    }

    public String getUltimaAlteracao() {
        return ultimaAlteracao;
    }

    public boolean isAplicaDesconto() {
        return aplicaDesconto;
    }

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public Empresa getEmpresaSelecionada() {
        return empresaSelecionada;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(final Parcel out, final int flags) {
        out.writeInt(idVendedor);
        out.writeString(codigo);
        out.writeString(nome);
        out.writeString(cpfCnpj);
        out.writeString(telefone);
        out.writeString(email);
        out.writeByte((byte)(ativo ? 1 : 0));
        out.writeInt(idTabela);
        out.writeString(ultimaAlteracao);
        out.writeByte((byte)(aplicaDesconto ? 1 : 0));
        out.writeTypedList(empresas);
        out.writeParcelable(empresaSelecionada, flags);
    }
}
