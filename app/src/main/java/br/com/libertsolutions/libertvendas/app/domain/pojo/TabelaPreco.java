package br.com.libertsolutions.libertvendas.app.domain.pojo;

import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class TabelaPreco {
    private final int idTabela;

    private final String codigo;

    private final String nome;

    private final boolean ativo;

    private final String ultimaAlteracao;

    private final List<ItemTabela> itensTabela;

    public TabelaPreco(
            int pIdTabela, String pCodigo, String pNome, boolean pAtivo,
            String pUltimaAlteracao, List<ItemTabela> pItensTabela) {
        idTabela = pIdTabela;
        codigo = pCodigo;
        nome = pNome;
        ativo = pAtivo;
        ultimaAlteracao = pUltimaAlteracao;
        itensTabela = pItensTabela;
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
}
