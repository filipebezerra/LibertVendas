package br.com.libertsolutions.libertvendas.app.domain.pojo;

/**
 * @author Filipe Bezerra
 */
public class FormaPagamento {
    private final int idFormaPagamento;

    private final String codigo;

    private final String descricao;

    private final float percentualDesconto;

    private final int idEmpresa;

    private final String ultimaAlteracao;

    private final boolean ativo;

    public FormaPagamento(
            int pIdFormaPagamento, String pCodigo, String pDescricao,
            float pPercentualDesconto, int pIdEmpresa, String pUltimaAlteracao, boolean pAtivo) {
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

    @Override
    public String toString() {
        return getDescricao();
    }

    @Override
    public boolean equals(Object pAnotherFormaPagamento) {
        if (this == pAnotherFormaPagamento) {
            return true;
        }
        if (pAnotherFormaPagamento == null || getClass() != pAnotherFormaPagamento.getClass()) {
            return false;
        }

        FormaPagamento that = (FormaPagamento) pAnotherFormaPagamento;

        return getIdFormaPagamento() == that.getIdFormaPagamento();
    }

    @Override
    public int hashCode() {
        return getIdFormaPagamento();
    }
}
