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
            int pIdFormaPagamento, String pCodigo, String pDescricao, float pPercentualDesconto,
            int pIdEmpresa, String pUltimaAlteracao, boolean pAtivo) {
        return new FormaPagamento(pIdFormaPagamento, pCodigo, pDescricao, pPercentualDesconto,
                pIdEmpresa, pUltimaAlteracao, pAtivo);
    }

    private FormaPagamento(Parcel in) {
        idFormaPagamento = in.readInt();
        codigo = in.readString();
        descricao = in.readString();
        percentualDesconto = in.readFloat();
        idEmpresa = in.readInt();
        ultimaAlteracao = in.readString();
        ativo = in.readByte() == 1;
    }

    private FormaPagamento(
            int pIdFormaPagamento, String pCodigo, String pDescricao, float pPercentualDesconto,
            int pIdEmpresa, String pUltimaAlteracao, boolean pAtivo) {
        idFormaPagamento = pIdFormaPagamento;
        codigo = pCodigo;
        descricao = pDescricao;
        percentualDesconto = pPercentualDesconto;
        idEmpresa = pIdEmpresa;
        ultimaAlteracao = pUltimaAlteracao;
        ativo = pAtivo;
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

    @Override public String toString() {
        return getDescricao();
    }

    @Override public boolean equals(Object anotherFormaPagamento) {
        if (this == anotherFormaPagamento) {
            return true;
        }
        if (anotherFormaPagamento == null || getClass() != anotherFormaPagamento.getClass()) {
            return false;
        }

        FormaPagamento that = (FormaPagamento) anotherFormaPagamento;

        return getIdFormaPagamento() == that.getIdFormaPagamento();
    }

    @Override public int hashCode() {
        return getIdFormaPagamento();
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
    }
}
