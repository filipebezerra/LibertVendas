package br.com.libertsolutions.libertvendas.app.domain.entity;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * @author Filipe Bezerra
 */
@RealmClass public class ItemTabelaEntity implements RealmModel {
    @PrimaryKey private Integer idItemTabela;

    @Required private Double precoVenda;

    @Required private Integer idProduto;

    private String ultimaAlteracao;

    public Integer getIdItemTabela() {
        return idItemTabela;
    }

    public ItemTabelaEntity setIdItemTabela(Integer pIdItemTabela) {
        idItemTabela = pIdItemTabela;
        return this;
    }

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public ItemTabelaEntity setPrecoVenda(Double pPrecoVenda) {
        precoVenda = pPrecoVenda;
        return this;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public ItemTabelaEntity setIdProduto(Integer pIdProduto) {
        idProduto = pIdProduto;
        return this;
    }

    public String getUltimaAlteracao() {
        return ultimaAlteracao;
    }

    public ItemTabelaEntity setUltimaAlteracao(String pUltimaAlteracao) {
        ultimaAlteracao = pUltimaAlteracao;
        return this;
    }
}
