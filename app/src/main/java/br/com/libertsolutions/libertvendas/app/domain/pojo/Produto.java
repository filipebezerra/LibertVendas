package br.com.libertsolutions.libertvendas.app.domain.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Filipe Bezerra
 */
public final class Produto implements Parcelable {

    private final int idProduto;

    private final String codigo;

    private final String codigoBarras;

    private final String descricao;

    private final String unidade;

    private final String grupo;

    private final float quantidade;

    private final String observacao;

    private final boolean ativo;

    private final String ultimaAlteracao;

    public static final Creator<Produto> CREATOR = new Creator<Produto>() {
        @Override public Produto createFromParcel(Parcel in) {
            return new Produto(in);
        }

        @Override public Produto[] newArray(int size) {
            return new Produto[size];
        }
    };

    public static Produto create(
            final int idProduto, final String codigo, final String codigoBarras,
            final String descricao, final String unidade, final String grupo, final float quantidade,
            final String observacao, final boolean ativo, final String ultimaAlteracao) {
        return new Produto(idProduto, codigo, codigoBarras, descricao, unidade, grupo,
                quantidade, observacao, ativo, ultimaAlteracao);
    }

    private Produto(Parcel in) {
        idProduto = in.readInt();
        codigo = in.readString();
        codigoBarras = in.readString();
        descricao = in.readString();
        unidade = in.readString();
        grupo = in.readString();
        quantidade = in.readFloat();
        observacao = in.readString();
        ativo = in.readByte() == 1;
        ultimaAlteracao = in.readString();
    }

    private Produto(
            final int idProduto, final String codigo, final String codigoBarras,
            final String descricao, final String unidade, final String grupo,
            final float quantidade, final String observacao, final boolean ativo,
            final String ultimaAlteracao) {
        this.idProduto = idProduto;
        this.codigo = codigo;
        this.codigoBarras = codigoBarras;
        this.descricao = descricao;
        this.unidade = unidade;
        this.grupo = grupo;
        this.quantidade = quantidade;
        this.observacao = observacao;
        this.ativo = ativo;
        this.ultimaAlteracao = ultimaAlteracao;
    }

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

    public String getUnidade() {
        return unidade;
    }

    public String getGrupo() {
        return grupo;
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

    @Override public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Produto produto = (Produto) o;

        return getIdProduto() == produto.getIdProduto();
    }

    @Override public int hashCode() {
        return getIdProduto();
    }

    @Override public String toString() {
        return getDescricao();
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(final Parcel out, final int flags) {
        out.writeInt(idProduto);
        out.writeString(codigo);
        out.writeString(codigoBarras);
        out.writeString(descricao );
        out.writeString(unidade);
        out.writeString(grupo);
        out.writeFloat(quantidade);
        out.writeString(observacao);
        out.writeByte((byte)(ativo ? 1 : 0));
        out.writeString(ultimaAlteracao);
    }
}
