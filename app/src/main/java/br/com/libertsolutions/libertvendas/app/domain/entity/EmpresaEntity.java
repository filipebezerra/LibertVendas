package br.com.libertsolutions.libertvendas.app.domain.entity;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * @author Filipe Bezerra
 */
@RealmClass public class EmpresaEntity implements RealmModel {
    private int idEmpresa;

    private String nome;

    private String cnpj;

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public EmpresaEntity setIdEmpresa(int pIdEmpresa) {
        idEmpresa = pIdEmpresa;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public EmpresaEntity setNome(String pNome) {
        nome = pNome;
        return this;
    }

    public String getCnpj() {
        return cnpj;
    }

    public EmpresaEntity setCnpj(String pCnpj) {
        cnpj = pCnpj;
        return this;
    }
}
