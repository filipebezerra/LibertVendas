package br.com.libertsolutions.libertvendas.app.domain.entity;

import br.com.libertsolutions.libertvendas.app.data.repository.Entity;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * @author Filipe Bezerra
 */
@RealmClass public class CidadeEntity implements Entity<Integer>, RealmModel {

    @PrimaryKey private Integer idCidade;

    private String codMunicipio;

    @Required private String nome;

    private EstadoEntity estado;

    @Override public CidadeEntity setId(final Integer id) {
        idCidade = id;
        return this;
    }

    @Override public Integer getId() {
        return idCidade;
    }

    public String getCodMunicipio() {
        return codMunicipio;
    }

    public CidadeEntity setCodMunicipio(final String codMunicipio) {
        this.codMunicipio = codMunicipio;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public CidadeEntity setNome(final String nome) {
        this.nome = nome;
        return this;
    }

    public EstadoEntity getEstado() {
        return estado;
    }

    public CidadeEntity setEstado(final EstadoEntity estado) {
        this.estado = estado;
        return this;
    }
}
