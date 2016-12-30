package br.com.libertsolutions.libertvendas.app.domain.vo;

import android.os.Parcel;
import android.os.Parcelable;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemTabela;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Produto;

/**
 * @author Filipe Bezerra
 */
public final class ProdutoVo implements Parcelable {

    private final ItemTabela itemTabela;

    private double totalProdutos = 0;

    private float quantidadeAdicionada = 0;

    public static ProdutoVo create(final ItemTabela itemTabela) {
        return new ProdutoVo(itemTabela);
    }

    public static final Creator<ProdutoVo> CREATOR = new Creator<ProdutoVo>() {
        @Override public ProdutoVo createFromParcel(Parcel in) {
            return new ProdutoVo(in);
        }

        @Override public ProdutoVo[] newArray(int size) {
            return new ProdutoVo[size];
        }
    };

    private ProdutoVo(Parcel in) {
        itemTabela = in.readParcelable(ItemTabela.class.getClassLoader());
        totalProdutos = in.readDouble();
        quantidadeAdicionada = in.readFloat();
    }

    private ProdutoVo(final ItemTabela itemTabela) {
        this.itemTabela = itemTabela;
    }

    public String getNome() {
        return itemTabela.getProduto().getDescricao();
    }

    public double getPreco() {
        return itemTabela.getPrecoVenda();
    }

    public double getTotalProdutos() {
        return totalProdutos;
    }

    public float getQuantidadeAdicionada() {
        return quantidadeAdicionada;
    }

    public Produto getProduto() {
        return itemTabela.getProduto();
    }

    public synchronized void addQuantidade() {
        quantidadeAdicionada++;
        calcularTotalProdutos();
    }

    public synchronized void setQuantidade(float pQuantidade) {
        quantidadeAdicionada = pQuantidade;
        calcularTotalProdutos();
    }

    public synchronized boolean removeQuantidade() {
        if (quantidadeAdicionada > 0) {
            quantidadeAdicionada--;
            calcularTotalProdutos();
            return true;
        }
        return false;
    }

    private void calcularTotalProdutos() {
        totalProdutos = quantidadeAdicionada * itemTabela.getPrecoVenda();
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(final Parcel out, final int flags) {
        out.writeParcelable(itemTabela, flags);
        out.writeDouble(totalProdutos);
        out.writeFloat(quantidadeAdicionada);
    }
}
