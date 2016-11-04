package br.com.libertsolutions.libertvendas.app.domain.entity;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * @author Filipe Bezerra
 */
@RealmClass public class EstadoEntity implements RealmModel {
    @PrimaryKey private int idEstado;

    @Required private String uf;

    @Required private String nome;

    public int getIdEstado() {
        return idEstado;
    }

    public EstadoEntity setIdEstado(int pIdEstado) {
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
