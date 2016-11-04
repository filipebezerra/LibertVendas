package br.com.libertsolutions.libertvendas.app.domain.entity;

import br.com.libertsolutions.libertvendas.app.data.util.RealmAutoIncrement;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * @author Filipe Bezerra
 */
@RealmClass public class ClienteEntity implements RealmModel {
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

    public Integer getId() {
        return id;
    }

    public ClienteEntity setId(Integer pId) {
        if (pId > 0) {
            id = pId;
        }
        return this;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public ClienteEntity setIdCliente(Integer pIdCliente) {
        idCliente = pIdCliente;
        return this;
    }

    public String getCodigo() {
        return codigo;
    }

    public ClienteEntity setCodigo(String pCodigo) {
        codigo = pCodigo;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public ClienteEntity setNome(String pNome) {
        nome = pNome;
        return this;
    }

    public Integer getTipo() {
        return tipo;
    }

    public ClienteEntity setTipo(Integer pTipo) {
        tipo = pTipo;
        return this;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public ClienteEntity setCpfCnpj(String pCpfCnpj) {
        cpfCnpj = pCpfCnpj;
        return this;
    }

    public String getContato() {
        return contato;
    }

    public ClienteEntity setContato(String pContato) {
        contato = pContato;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ClienteEntity setEmail(String pEmail) {
        email = pEmail;
        return this;
    }

    public String getTelefone() {
        return telefone;
    }

    public ClienteEntity setTelefone(String pTelefone) {
        telefone = pTelefone;
        return this;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public ClienteEntity setTelefone2(String pTelefone2) {
        telefone2 = pTelefone2;
        return this;
    }

    public String getEndereco() {
        return endereco;
    }

    public ClienteEntity setEndereco(String pEndereco) {
        endereco = pEndereco;
        return this;
    }

    public String getCep() {
        return cep;
    }

    public ClienteEntity setCep(String pCep) {
        cep = pCep;
        return this;
    }

    public CidadeEntity getCidade() {
        return cidade;
    }

    public ClienteEntity setCidade(CidadeEntity pCidade) {
        cidade = pCidade;
        return this;
    }

    public String getBairro() {
        return bairro;
    }

    public ClienteEntity setBairro(String pBairro) {
        bairro = pBairro;
        return this;
    }

    public String getNumero() {
        return numero;
    }

    public ClienteEntity setNumero(String pNumero) {
        numero = pNumero;
        return this;
    }

    public String getComplemento() {
        return complemento;
    }

    public ClienteEntity setComplemento(String pComplemento) {
        complemento = pComplemento;
        return this;
    }

    public String getUltimaAlteracao() {
        return ultimaAlteracao;
    }

    public ClienteEntity setUltimaAlteracao(String pUltimaAlteracao) {
        ultimaAlteracao = pUltimaAlteracao;
        return this;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public ClienteEntity setAtivo(Boolean pAtivo) {
        ativo = pAtivo;
        return this;
    }
}
