package br.com.libertsolutions.libertvendas.app.domain.entity;

import br.com.libertsolutions.libertvendas.app.data.repository.Entity;
import io.realm.RealmModel;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * @author Filipe Bezerra
 */
@RealmClass public class EstadoEntity implements Entity<Integer>, RealmModel {

    @PrimaryKey private Integer idEstado;

    @Required private String uf;

    @Required @Index private String nome;

    @Override public EstadoEntity setId(final Integer id) {
        idEstado = id;
        return this;
    }

    @Override public Integer getId() {
        return idEstado;
    }

    public String getUf() {
        return uf;
    }

    public EstadoEntity setUf(final String uf) {
        this.uf = uf;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public EstadoEntity setNome(final String nome) {
        this.nome = nome;
        return this;
    }
}
