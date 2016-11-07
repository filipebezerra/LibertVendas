package br.com.libertsolutions.libertvendas.app.domain.pojo;

/**
 * @author Filipe Bezerra
 */
public class ItemTabela {
    private final int idItemTabela;

    private final double precoVenda;

    private final int idProduto;

    private final String ultimaAlteracao;

    public ItemTabela(
            int pIdItemTabela, double pPrecoVenda, int pIdProduto, String pUltimaAlteracao) {
        idItemTabela = pIdItemTabela;
        precoVenda = pPrecoVenda;
        idProduto = pIdProduto;
        ultimaAlteracao = pUltimaAlteracao;
    }

    public int getIdItemTabela() {
        return idItemTabela;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public String getUltimaAlteracao() {
        return ultimaAlteracao;
    }
}
