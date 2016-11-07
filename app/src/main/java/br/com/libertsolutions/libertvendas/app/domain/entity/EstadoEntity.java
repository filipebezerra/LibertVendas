package br.com.libertsolutions.libertvendas.app.domain.entity;

import io.realm.RealmModel;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * @author Filipe Bezerra
 */
@RealmClass public class EstadoEntity implements RealmModel {
    public static final String FIELD_NAME_NOME = "nome";

    @PrimaryKey private Integer idEstado;

    @Required private String uf;

    @Required @Index private String nome;

    public Integer getIdEstado() {
        return idEstado;
    }

    public EstadoEntity setIdEstado(Integer pIdEstado) {
        idEstado = pIdEstado;
        return this;
    }

    public String getUf() {
        return uf;
    }

    public EstadoEntity setUf(String pUf) {
        uf = pUf;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public EstadoEntity setNome(String pNome) {
        nome = pNome;
        return this;
    }
}
