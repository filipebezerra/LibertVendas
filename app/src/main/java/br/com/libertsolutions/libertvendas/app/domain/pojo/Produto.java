package br.com.libertsolutions.libertvendas.app.domain.pojo;

/**
 * @author Filipe Bezerra
 */
public class Produto {
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
}
