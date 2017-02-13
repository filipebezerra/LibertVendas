package br.com.libertsolutions.libertvendas.app.domain.entity;

import br.com.libertsolutions.libertvendas.app.data.repository.Entity;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * @author Filipe Bezerra
 */
@RealmClass public class EmpresaEntity implements Entity<Integer>, RealmModel {

    @PrimaryKey private Integer idEmpresa;

    @Required private String nome;

    @Required private String cnpj;

    @Required private Integer idTabela;

    @Override public EmpresaEntity setId(final Integer id) {
        idEmpresa = id;
        return this;
    }

    @Override public Integer getId() {
        return idEmpresa;
    }

    public String getNome() {
        return nome;
    }

    public EmpresaEntity setNome(final String nome) {
        this.nome = nome;
        return this;
    }

    public String getCnpj() {
        return cnpj;
    }

    public EmpresaEntity setCnpj(final String cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public Integer getIdTabela() {
        return idTabela;
    }

    public EmpresaEntity setIdTabela(final Integer idTabela) {
        this.idTabela = idTabela;
        return this;
    }
}
