package br.com.libertsolutions.libertvendas.app.domain.entity;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * @author Filipe Bezerra
 */
@RealmClass public class VendedorEntity implements RealmModel {
    @PrimaryKey private Integer idVendedor;

    private String codigo;

    @Required private String nome;

    @Required private String cpfCnpj;

    private String telefone;

    private String email;

    private Boolean ativo;

    private Integer idTabela;

    private String ultimaAlteracao;

    public Integer getIdVendedor() {
        return idVendedor;
    }

    public VendedorEntity setIdVendedor(Integer pIdVendedor) {
        idVendedor = pIdVendedor;
        return this;
    }

    public String getCodigo() {
        return codigo;
    }

    public VendedorEntity setCodigo(String pCodigo) {
        codigo = pCodigo;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public VendedorEntity setNome(String pNome) {
        nome = pNome;
        return this;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public VendedorEntity setCpfCnpj(String pCpfCnpj) {
        cpfCnpj = pCpfCnpj;
        return this;
    }

    public String getTelefone() {
        return telefone;
    }

    public VendedorEntity setTelefone(String pTelefone) {
        telefone = pTelefone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public VendedorEntity setEmail(String pEmail) {
        email = pEmail;
        return this;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public VendedorEntity setAtivo(Boolean pAtivo) {
        ativo = pAtivo;
        return this;
    }

    public Integer getIdTabela() {
        return idTabela;
    }

    public VendedorEntity setIdTabela(Integer pIdTabela) {
        idTabela = pIdTabela;
        return this;
    }

    public String getUltimaAlteracao() {
        return ultimaAlteracao;
    }

    public VendedorEntity setUltimaAlteracao(String pUltimaAlteracao) {
        ultimaAlteracao = pUltimaAlteracao;
        return this;
    }
}
