package br.com.libertsolutions.libertvendas.app.domain.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Filipe Bezerra
 */
public class Produto implements Parcelable {
    private final int idProduto;

    private final String codigo;

    private final String codigoBarras;

    private final String descricao;

    private final String unidadeMedida;

    private final String grupo;

    private final double precoVenda;

    private final float quantidade;

    private final String observacao;

    private final boolean ativo;

    private final String ultimaAlteracao;

    public Produto(int pIdProduto, String pCodigo, String pCodigoBarras, String pDescricao,
            String pUnidadeMedida, String pGrupo, double pPrecoVenda, float pQuantidade,
            String pObservacao, boolean pAtivo, String pUltimaAlteracao) {
        idProduto = pIdProduto;
        codigo = pCodigo;
        codigoBarras = pCodigoBarras;
        descricao = pDescricao;
        unidadeMedida = pUnidadeMedida;
        grupo = pGrupo;
        precoVenda = pPrecoVenda;
        quantidade = pQuantidade;
        observacao = pObservacao;
        ativo = pAtivo;
        ultimaAlteracao = pUltimaAlteracao;
    }

    protected Produto(Parcel in) {
        idProduto = in.readInt();
        codigo = in.readString();
        codigoBarras = in.readString();
        descricao = in.readString();
        unidadeMedida = in.readString();
        grupo = in.readString();
        precoVenda = in.readDouble();
        quantidade = in.readFloat();
        observacao = in.readString();
        ativo = in.readByte() != 0;
        ultimaAlteracao = in.readString();
    }

    public static final Creator<Produto> CREATOR = new Creator<Produto>() {
        @Override public Produto createFromParcel(Parcel in) {
            return new Produto(in);
        }

        @Override public Produto[] newArray(int size) {
            return new Produto[size];
        }
    };

    public int getIdProduto() {
        return idProduto;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public String getGrupo() {
        return grupo;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public float getQuantidade() {
        return quantidade;
    }

    public String getObservacao() {
        return observacao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public String getUltimaAlteracao() {
        return ultimaAlteracao;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel pOut, int pFlags) {
        pOut.writeInt(idProduto);
        pOut.writeString(codigo);
        pOut.writeString(codigoBarras);
        pOut.writeString(descricao);
        pOut.writeString(unidadeMedida);
        pOut.writeString(grupo);
        pOut.writeDouble(precoVenda);
        pOut.writeFloat(quantidade);
        pOut.writeString(observacao);
        pOut.writeByte((byte) (ativo ? 1 : 0));
        pOut.writeString(ultimaAlteracao);
    }
}
