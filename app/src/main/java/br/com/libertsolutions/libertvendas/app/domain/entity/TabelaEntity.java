package br.com.libertsolutions.libertvendas.app.domain.entity;

import br.com.libertsolutions.libertvendas.app.data.repository.Entity;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * @author Filipe Bezerra
 */
@RealmClass public class TabelaEntity implements Entity<Integer>, RealmModel {

    @PrimaryKey private Integer idTabela;

    private String codigo;

    @Required private String nome;

    private Boolean ativo;

    private String ultimaAlteracao;

    private RealmList<ItemTabelaEntity> itensTabela;

    @Override public TabelaEntity setId(final Integer id) {
        idTabela = id;
        return this;
    }

    @Override public Integer getId() {
        return idTabela;
    }

    public String getCodigo() {
        return codigo;
    }

    public TabelaEntity setCodigo(final String codigo) {
        this.codigo = codigo;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public TabelaEntity setNome(final String nome) {
        this.nome = nome;
        return this;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public TabelaEntity setAtivo(final Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public String getUltimaAlteracao() {
        return ultimaAlteracao;
    }

    public TabelaEntity setUltimaAlteracao(final String ultimaAlteracao) {
        this.ultimaAlteracao = ultimaAlteracao;
        return this;
    }

    public RealmList<ItemTabelaEntity> getItensTabela() {
        return itensTabela;
    }

    public TabelaEntity setItensTabela(final RealmList<ItemTabelaEntity> itensTabela) {
        this.itensTabela = itensTabela;
        return this;
    }
}
