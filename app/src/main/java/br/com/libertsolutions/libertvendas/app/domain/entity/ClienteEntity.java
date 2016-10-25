package br.com.libertsolutions.libertvendas.app.domain.entity;

import br.com.libertsolutions.libertvendas.app.data.util.RealmAutoIncrement;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * @author Filipe Bezerra
 */
@RealmClass
public class ClienteEntity implements RealmModel {
    @PrimaryKey
    private long id = RealmAutoIncrement.getInstance(ClienteEntity.class).getNextIdFromModel();

    private String codigo;

    @Required
    private String nome;

    private int tipo;

    @Required
    private String cpfOuCnpj;

    private String email;

    private String telefone;

    private String telefone2;

    private String endereco;

    private String cep;

    private String bairro;

    private String numero;

    private String complemento;

    @Required
    private String cidade;

    @Required
    private String uf;

    public long getId() {
        return id;
    }

    public ClienteEntity setId(long pId) {
        id = pId;
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

    public int getTipo() {
        return tipo;
    }

    public ClienteEntity setTipo(int pTipo) {
        tipo = pTipo;
        return this;
    }

    public String getCpfOuCnpj() {
        return cpfOuCnpj;
    }

    public ClienteEntity setCpfOuCnpj(String pCpfOuCnpj) {
        cpfOuCnpj = pCpfOuCnpj;
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

    public String getCidade() {
        return cidade;
    }

    public ClienteEntity setCidade(String pCidade) {
        cidade = pCidade;
        return this;
    }

    public String getUf() {
        return uf;
    }

    public ClienteEntity setUf(String pUf) {
        uf = pUf;
        return this;
    }
}
