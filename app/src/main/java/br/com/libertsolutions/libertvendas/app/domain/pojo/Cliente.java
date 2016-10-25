package br.com.libertsolutions.libertvendas.app.domain.pojo;

/**
 * @author Filipe Bezerra
 */
public class Cliente {
    private final long id;

    private final String codigo;

    private final String nome;

    private final int tipo;

    private final String cpfOuCnpj;

    private final String contato;

    private final String email;

    private final String telefone;

    private final String telefone2;

    private final String endereco;

    private final String cep;

    private final String bairro;

    private final String numero;

    private final String complemento;

    private final String cidade;

    private final String uf;

    public Cliente(long pId, String pCodigo, String pNome, int pTipo, String pCpfOuCnpj,
            String pContato, String pEmail, String pTelefone, String pTelefone2,
            String pEndereco, String pCep, String pBairro, String pNumero,
            String pComplemento, String pCidade, String pUf) {
        id = pId;
        codigo = pCodigo;
        nome = pNome;
        tipo = pTipo;
        cpfOuCnpj = pCpfOuCnpj;
        contato = pContato;
        email = pEmail;
        telefone = pTelefone;
        telefone2 = pTelefone2;
        endereco = pEndereco;
        cep = pCep;
        bairro = pBairro;
        numero = pNumero;
        complemento = pComplemento;
        cidade = pCidade;
        uf = pUf;
    }

    public long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public int getTipo() {
        return tipo;
    }

    public String getCpfOuCnpj() {
        return cpfOuCnpj;
    }

    public String getContato() {
        return contato;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getCep() {
        return cep;
    }

    public String getBairro() {
        return bairro;
    }

    public String getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public String getUf() {
        return uf;
    }

    @Override
    public boolean equals(Object pO) {
        if (this == pO) {
            return true;
        }
        if (pO == null || getClass() != pO.getClass()) {
            return false;
        }

        Cliente cliente = (Cliente) pO;

        return getCpfOuCnpj().equals(cliente.getCpfOuCnpj());
    }

    @Override
    public int hashCode() {
        return getCpfOuCnpj().hashCode();
    }
}
