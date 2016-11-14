package br.com.libertsolutions.libertvendas.app.domain.entity;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * @author Filipe Bezerra
 */
@RealmClass public class TabelaPrecoEntity implements RealmModel {
    public static final String ID_FIELD_NAME = "idTabela";

    @PrimaryKey private Integer idTabela;

    private String codigo;

    @Required private String nome;

    private Boolean ativo;

    private String ultimaAlteracao;

    private RealmList<ItemTabelaEntity> itensTabela;

    public Integer getIdTabela() {
        return idTabela;
    }

    public TabelaPrecoEntity setIdTabela(Integer pIdTabela) {
        idTabela = pIdTabela;
        return this;
    }

    public String getCodigo() {
        return codigo;
    }

    public TabelaPrecoEntity setCodigo(String pCodigo) {
        codigo = pCodigo;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public TabelaPrecoEntity setNome(String pNome) {
        nome = pNome;
        return this;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public TabelaPrecoEntity setAtivo(Boolean pAtivo) {
        ativo = pAtivo;
        return this;
    }

    public String getUltimaAlteracao() {
        return ultimaAlteracao;
    }

    public TabelaPrecoEntity setUltimaAlteracao(String pUltimaAlteracao) {
        ultimaAlteracao = pUltimaAlteracao;
        return this;
    }

    public RealmList<ItemTabelaEntity> getItensTabela() {
        return itensTabela;
    }

    public TabelaPrecoEntity setItensTabela(RealmList<ItemTabelaEntity> pItensTabela) {
        itensTabela = pItensTabela;
        return this;
    }
}
