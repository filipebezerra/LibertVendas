package br.com.libertsolutions.libertvendas.app.domain.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public final class Tabela implements Parcelable {

    private final int idTabela;

    private final String codigo;

    private final String nome;

    private final boolean ativo;

    private final String ultimaAlteracao;

    private final List<ItemTabela> itensTabela;

    private final String cnpjEmpresa;

    private final String cpfCnpjVendedor;

    public static final Creator<Tabela> CREATOR = new Creator<Tabela>() {
        @Override public Tabela createFromParcel(Parcel in) {
            return new Tabela(in);
        }

        @Override public Tabela[] newArray(int size) {
            return new Tabela[size];
        }
    };

    public static Tabela create(
            final int idTabela,
            final String codigo,
            final String nome,
            final boolean ativo,
            final String ultimaAlteracao,
            final List<ItemTabela> itensTabela,
            final String cpfCnpjVendedor,
            final String cnpjEmpresa
    ) {
        return new Tabela(
                idTabela,
                codigo,
                nome,
                ativo,
                ultimaAlteracao,
                itensTabela,
                cpfCnpjVendedor,
                cnpjEmpresa
        );
    }

    private Tabela(Parcel in) {
        idTabela = in.readInt();
        codigo = in.readString();
        nome = in.readString();
        ativo = in.readByte() == 1;
        ultimaAlteracao = in.readString();
        itensTabela = in.createTypedArrayList(ItemTabela.CREATOR);
        cpfCnpjVendedor = in.readString();
        cnpjEmpresa = in.readString();
    }

    private Tabela(
            int idTabela,
            String codigo,
            String nome,
            boolean ativo,
            String ultimaAlteracao,
            List<ItemTabela> itensTabela,
            String cpfCnpjVendedor,
            String cnpjEmpresa
    ) {
        this.idTabela = idTabela;
        this.codigo = codigo;
        this.nome = nome;
        this.ativo = ativo;
        this.ultimaAlteracao = ultimaAlteracao;
        this.itensTabela = itensTabela;
        this.cpfCnpjVendedor = cpfCnpjVendedor;
        this.cnpjEmpresa = cnpjEmpresa;
    }

    public int getIdTabela() {
        return idTabela;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public String getUltimaAlteracao() {
        return ultimaAlteracao;
    }

    public List<ItemTabela> getItensTabela() {
        return itensTabela;
    }

    public String getCnpjEmpresa() {
        return cnpjEmpresa;
    }

    public String getCpfCnpjVendedor() {
        return cpfCnpjVendedor;
    }

    @Override public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Tabela tabela = (Tabela) o;

        if (getIdTabela() != tabela.getIdTabela()) {
            return false;
        }
        if (!getCnpjEmpresa().equals(tabela.getCnpjEmpresa())) {
            return false;
        }
        return getCpfCnpjVendedor().equals(tabela.getCpfCnpjVendedor());
    }

    @Override public int hashCode() {
        int result = getIdTabela();
        result = 31 * result + getCnpjEmpresa().hashCode();
        result = 31 * result + getCpfCnpjVendedor().hashCode();
        return result;
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("Tabela{");
        sb.append("idTabela=").append(idTabela);
        sb.append(", codigo='").append(codigo).append('\'');
        sb.append(", nome='").append(nome).append('\'');
        sb.append(", ativo=").append(ativo);
        sb.append(", ultimaAlteracao='").append(ultimaAlteracao).append('\'');
        sb.append(", itensTabela=").append(itensTabela);
        sb.append(", cnpjEmpresa='").append(cnpjEmpresa).append('\'');
        sb.append(", cpfCnpjVendedor='").append(cpfCnpjVendedor).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(final Parcel out, final int flags) {
        out.writeInt(idTabela);
        out.writeString(codigo);
        out.writeString(nome);
        out.writeByte((byte)(ativo ? 1 : 0));
        out.writeString(ultimaAlteracao);
        out.writeTypedList(itensTabela);
        out.writeString(cpfCnpjVendedor);
        out.writeString(cnpjEmpresa);
    }
}
