package br.com.libertsolutions.libertvendas.app.domain.pojo;

/**
 * @author Filipe Bezerra
 */
public class FormaPagamento {
    private final String descricao;

    public FormaPagamento(String pDescricao) {
        descricao = pDescricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
