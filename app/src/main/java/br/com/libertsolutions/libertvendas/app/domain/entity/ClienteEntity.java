package br.com.libertsolutions.libertvendas.app.domain.entity;

import br.com.libertsolutions.libertvendas.app.data.repository.Entity;
import br.com.libertsolutions.libertvendas.app.data.utils.RealmAutoIncrement;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * @author Filipe Bezerra
 */
@RealmClass public class ClienteEntity implements Entity<Integer>, RealmModel {

    @PrimaryKey private Integer id
            = RealmAutoIncrement.getInstance(this.getClass()).getNextIdFromModel();

    @Required private Integer idCliente;

    private String codigo;

    @Required private String nome;

    private Integer tipo;

    @Required private String cpfCnpj;

    private String contato;

    private String email;

    private String telefone;

    private String telefone2;

    private String endereco;

    private String cep;

    private CidadeEntity cidade;

    private String bairro;

    private String numero;

    private String complemento;

    private String ultimaAlteracao;

    private Boolean ativo;

    private String cnpjEmpresa;

    private String cpfCnpjVendedor;

    private Integer status;

    private String nomeFantasia;

    private Integer tabelaPadrao;

    @Override public ClienteEntity setId(final Integer id) {
        if (id > 0) {
            this.id = id;
        }
        return this;
    }

    @Override public Integer getId() {
        return id;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public ClienteEntity setIdCliente(final Integer idCliente) {
        this.idCliente = idCliente;
        return this;
    }

    public String getCodigo() {
        return codigo;
    }

    public ClienteEntity setCodigo(final String codigo) {
        this.codigo = codigo;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public ClienteEntity setNome(final String nome) {
        this.nome = nome;
        return this;
    }

    public Integer getTipo() {
        return tipo;
    }

    public ClienteEntity setTipo(final Integer tipo) {
        this.tipo = tipo;
        return this;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public ClienteEntity setCpfCnpj(final String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
        return this;
    }

    public String getContato() {
        return contato;
    }

    public ClienteEntity setContato(final String contato) {
        this.contato = contato;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ClienteEntity setEmail(final String email) {
        this.email = email;
        return this;
    }

    public String getTelefone() {
        return telefone;
    }

    public ClienteEntity setTelefone(final String telefone) {
        this.telefone = telefone;
        return this;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public ClienteEntity setTelefone2(final String telefone2) {
        this.telefone2 = telefone2;
        return this;
    }

    public String getEndereco() {
        return endereco;
    }

    public ClienteEntity setEndereco(final String endereco) {
        this.endereco = endereco;
        return this;
    }

    public String getCep() {
        return cep;
    }

    public ClienteEntity setCep(final String cep) {
        this.cep = cep;
        return this;
    }

    public CidadeEntity getCidade() {
        return cidade;
    }

    public ClienteEntity setCidade(
            final CidadeEntity cidade) {
        this.cidade = cidade;
        return this;
    }

    public String getBairro() {
        return bairro;
    }

    public ClienteEntity setBairro(final String bairro) {
        this.bairro = bairro;
        return this;
    }

    public String getNumero() {
        return numero;
    }

    public ClienteEntity setNumero(final String numero) {
        this.numero = numero;
        return this;
    }

    public String getComplemento() {
        return complemento;
    }

    public ClienteEntity setComplemento(final String complemento) {
        this.complemento = complemento;
        return this;
    }

    public String getUltimaAlteracao() {
        return ultimaAlteracao;
    }

    public ClienteEntity setUltimaAlteracao(final String ultimaAlteracao) {
        this.ultimaAlteracao = ultimaAlteracao;
        return this;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public ClienteEntity setAtivo(final Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public String getCnpjEmpresa() {
        return cnpjEmpresa;
    }

    public ClienteEntity setCnpjEmpresa(final String cnpjEmpresa) {
        this.cnpjEmpresa = cnpjEmpresa;
        return this;
    }

    public String getCpfCnpjVendedor() {
        return cpfCnpjVendedor;
    }

    public ClienteEntity setCpfCnpjVendedor(final String cpfCnpjVendedor) {
        this.cpfCnpjVendedor = cpfCnpjVendedor;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public ClienteEntity setStatus(final Integer status) {
        this.status = status;
        return this;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public ClienteEntity setNomeFantasia(final String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
        return this;
    }

    public Integer getTabelaPadrao() {
        return tabelaPadrao;
    }

    public ClienteEntity setTabelaPadrao(final Integer tabelaPadrao) {
        this.tabelaPadrao = tabelaPadrao;
        return this;
    }
}
