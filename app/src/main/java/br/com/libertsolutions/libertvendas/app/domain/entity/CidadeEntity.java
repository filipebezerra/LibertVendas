package br.com.libertsolutions.libertvendas.app.domain.entity;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * @author Filipe Bezerra
 */
@RealmClass public class CidadeEntity implements RealmModel {
    @PrimaryKey private Integer idCidade;

    private String codMunicipio;

    @Required private String nome;

    private EstadoEntity estado;

    public Integer getIdCidade() {
        return idCidade;
    }

    public CidadeEntity setIdCidade(Integer pIdCidade) {
        idCidade = pIdCidade;
        return this;
    }

    public String getCodMunicipio() {
        return codMunicipio;
    }

    public CidadeEntity setCodMunicipio(String pCodMunicipio) {
        codMunicipio = pCodMunicipio;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public CidadeEntity setNome(String pNome) {
        nome = pNome;
        return this;
    }

    public EstadoEntity getEstado() {
        return estado;
    }

    public CidadeEntity setEstado(EstadoEntity pEstado) {
        estado = pEstado;
        return this;
    }
}
