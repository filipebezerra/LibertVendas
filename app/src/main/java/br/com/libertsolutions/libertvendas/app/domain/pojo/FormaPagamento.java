package br.com.libertsolutions.libertvendas.app.domain.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Filipe Bezerra
 */
public final class FormaPagamento implements Parcelable {

    private final int idFormaPagamento;

    private final String codigo;

    private final String descricao;

    private final float percentualDesconto;

    private final int idEmpresa;

    private final String ultimaAlteracao;

    private final boolean ativo;

    private final String cnpjEmpresa;

    private final String cpfCnpjVendedor;

    public static final Creator<FormaPagamento> CREATOR = new Creator<FormaPagamento>() {
        @Override
        public FormaPagamento createFromParcel(Parcel in) {
            return new FormaPagamento(in);
        }

        @Override
        public FormaPagamento[] newArray(int size) {
            return new FormaPagamento[size];
        }
    };

    public static FormaPagamento create(
            final int idFormaPagamento,
            final String codigo,
            final String descricao,
            final float percentualDesconto,
            final int idEmpresa,
            final String ultimaAlteracao,
            final boolean ativo,
            final String cpfCnpjVendedor,
            final String cnpjEmpresa
    ) {
        return new FormaPagamento(
                idFormaPagamento,
                codigo,
                descricao,
                percentualDesconto,
                idEmpresa,
                ultimaAlteracao,
                ativo,
                cpfCnpjVendedor,
                cnpjEmpresa
        );
    }

    private FormaPagamento(Parcel in) {
        idFormaPagamento = in.readInt();
        codigo = in.readString();
        descricao = in.readString();
        percentualDesconto = in.readFloat();
        idEmpresa = in.readInt();
        ultimaAlteracao = in.readString();
        ativo = in.readByte() == 1;
        cpfCnpjVendedor = in.readString();
        cnpjEmpresa = in.readString();
    }

    private FormaPagamento(
            int idFormaPagamento,
            String codigo,
            String descricao,
            float percentualDesconto,
            int idEmpresa,
            String ultimaAlteracao,
            boolean ativo,
            String cpfCnpjVendedor,
            String cnpjEmpresa
    ) {
        this.idFormaPagamento = idFormaPagamento;
        this.codigo = codigo;
        this.descricao = descricao;
        this.percentualDesconto = percentualDesconto;
        this.idEmpresa = idEmpresa;
        this.ultimaAlteracao = ultimaAlteracao;
        this.ativo = ativo;
        this.cpfCnpjVendedor = cpfCnpjVendedor;
        this.cnpjEmpresa = cnpjEmpresa;
    }

    public int getIdFormaPagamento() {
        return idFormaPagamento;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public float getPercentualDesconto() {
        return percentualDesconto;
    }

    public int getIdEmpresa() {
        return idEmpresa;
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

    @Override public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FormaPagamento that = (FormaPagamento) o;

        if (getIdFormaPagamento() != that.getIdFormaPagamento()) {
            return false;
        }
        if (!getCnpjEmpresa().equals(that.getCnpjEmpresa())) {
            return false;
        }
        return getCpfCnpjVendedor().equals(that.getCpfCnpjVendedor());
    }

    @Override public int hashCode() {
        int result = getIdFormaPagamento();
        result = 31 * result + getCnpjEmpresa().hashCode();
        result = 31 * result + getCpfCnpjVendedor().hashCode();
        return result;
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("FormaPagamento{");
        sb.append("idFormaPagamento=").append(idFormaPagamento);
        sb.append(", codigo='").append(codigo).append('\'');
        sb.append(", descricao='").append(descricao).append('\'');
        sb.append(", percentualDesconto=").append(percentualDesconto);
        sb.append(", idEmpresa=").append(idEmpresa);
        sb.append(", ultimaAlteracao='").append(ultimaAlteracao).append('\'');
        sb.append(", ativo=").append(ativo);
        sb.append(", cnpjEmpresa='").append(cnpjEmpresa).append('\'');
        sb.append(", cpfCnpjVendedor='").append(cpfCnpjVendedor).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(final Parcel out, final int flags) {
        out.writeInt(idFormaPagamento);
        out.writeString(codigo);
        out.writeString(descricao);
        out.writeFloat(percentualDesconto);
        out.writeInt(idEmpresa);
        out.writeString(ultimaAlteracao);
        out.writeByte((byte) (ativo ? 1 : 0));
        out.writeString(cpfCnpjVendedor);
        out.writeString(cnpjEmpresa);
    }
}
