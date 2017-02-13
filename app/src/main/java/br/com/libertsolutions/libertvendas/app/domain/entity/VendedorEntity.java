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
@RealmClass public class VendedorEntity implements Entity<Integer>, RealmModel {

    @PrimaryKey private Integer idVendedor;

    private String codigo;

    @Required private String nome;

    @Required private String cpfCnpj;

    private String telefone;

    private String email;

    private Boolean ativo;

    private String ultimaAlteracao;

    private Boolean aplicaDesconto;

    private RealmList<EmpresaEntity> empresas;

    private EmpresaEntity empresaSelecionada;

    @Override public VendedorEntity setId(final Integer id) {
        idVendedor = id;
        return this;
    }

    @Override public Integer getId() {
        return idVendedor;
    }

    public String getCodigo() {
        return codigo;
    }

    public VendedorEntity setCodigo(final String codigo) {
        this.codigo = codigo;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public VendedorEntity setNome(final String nome) {
        this.nome = nome;
        return this;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public VendedorEntity setCpfCnpj(final String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
        return this;
    }

    public String getTelefone() {
        return telefone;
    }

    public VendedorEntity setTelefone(final String telefone) {
        this.telefone = telefone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public VendedorEntity setEmail(final String email) {
        this.email = email;
        return this;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public VendedorEntity setAtivo(final Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public String getUltimaAlteracao() {
        return ultimaAlteracao;
    }

    public VendedorEntity setUltimaAlteracao(final String ultimaAlteracao) {
        this.ultimaAlteracao = ultimaAlteracao;
        return this;
    }

    public Boolean isAplicaDesconto() {
        return aplicaDesconto;
    }

    public VendedorEntity setAplicaDesconto(final Boolean aplicaDesconto) {
        this.aplicaDesconto = aplicaDesconto;
        return this;
    }

    public RealmList<EmpresaEntity> getEmpresas() {
        return empresas;
    }

    public VendedorEntity setEmpresas(final RealmList<EmpresaEntity> empresas) {
        this.empresas = empresas;
        return this;
    }

    public EmpresaEntity getEmpresaSelecionada() {
        return empresaSelecionada;
    }

    public VendedorEntity setEmpresaSelecionada(final EmpresaEntity empresaSelecionada) {
        this.empresaSelecionada = empresaSelecionada;
        return this;
    }
}
