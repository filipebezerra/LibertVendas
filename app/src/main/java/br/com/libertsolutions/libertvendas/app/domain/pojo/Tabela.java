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

    public static final Creator<Tabela> CREATOR = new Creator<Tabela>() {
        @Override public Tabela createFromParcel(Parcel in) {
            return new Tabela(in);
        }

        @Override public Tabela[] newArray(int size) {
            return new Tabela[size];
        }
    };

    public static Tabela create(
            final int idTabela, final String codigo, final String nome, final boolean ativo,
            final String ultimaAlteracao, final List<ItemTabela> itensTabela) {
        return new Tabela(idTabela, codigo, nome, ativo, ultimaAlteracao, itensTabela);
    }

    private Tabela(Parcel in) {
        idTabela = in.readInt();
        codigo = in.readString();
        nome = in.readString();
        ativo = in.readByte() == 1;
        ultimaAlteracao = in.readString();
        itensTabela = in.createTypedArrayList(ItemTabela.CREATOR);
    }

    private Tabela(
            final int idTabela, final String codigo, final String nome, final boolean ativo,
            final String ultimaAlteracao, final List<ItemTabela> itensTabela) {
        this.idTabela = idTabela;
        this.codigo = codigo;
        this.nome = nome;
        this.ativo = ativo;
        this.ultimaAlteracao = ultimaAlteracao;
        this.itensTabela = itensTabela;
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
    }
}
