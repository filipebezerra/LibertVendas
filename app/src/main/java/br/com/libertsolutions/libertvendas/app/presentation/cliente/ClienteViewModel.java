package br.com.libertsolutions.libertvendas.app.presentation.cliente;

import br.com.libertsolutions.libertvendas.app.presentation.util.StringUtils;

/**
 * @author Filipe Bezerra
 */
class ClienteViewModel {
    int tipoPessoa = -1;
    String cpfCnpj = null;
    String nome = null;
    String email = null;
    String endereco = null;
    String numero = null;
    String bairro = null;
    String cidade = null;
    int estado = -1;
    String cep = null;
    String complemento = null;
    String telefone = null;
    String celular = null;

    public ClienteViewModel setTipoPessoa(int pTipoPessoa) {
        tipoPessoa = pTipoPessoa;
        return this;
    }

    public ClienteViewModel setCpfCnpj(String pCpfCnpj) {
        cpfCnpj = pCpfCnpj;
        return this;
    }

    public ClienteViewModel setNome(String pNome) {
        nome = pNome;
        return this;
    }

    public ClienteViewModel setEmail(String pEmail) {
        email = pEmail;
        return this;
    }

    public ClienteViewModel setEndereco(String pEndereco) {
        endereco = pEndereco;
        return this;
    }

    public ClienteViewModel setNumero(String pNumero) {
        numero = pNumero;
        return this;
    }

    public ClienteViewModel setBairro(String pBairro) {
        bairro = pBairro;
        return this;
    }

    public ClienteViewModel setCidade(String pCidade) {
        cidade = pCidade;
        return this;
    }

    public ClienteViewModel setEstado(int pEstado) {
        estado = pEstado;
        return this;
    }

    public ClienteViewModel setCep(String pCep) {
        cep = pCep;
        return this;
    }

    public ClienteViewModel setComplemento(String pComplemento) {
        complemento = pComplemento;
        return this;
    }

    public ClienteViewModel setTelefone(String pTelefone) {
        telefone = pTelefone;
        return this;
    }

    public ClienteViewModel setCelular(String pCelular) {
        celular = pCelular;
        return this;
    }

    boolean hasDefaultValues() {
        return (tipoPessoa == -1)
                && StringUtils.isEmpty(cpfCnpj)
                && StringUtils.isEmpty(nome)
                && StringUtils.isEmpty(email)
                && StringUtils.isEmpty(endereco)
                && StringUtils.isEmpty(numero)
                && StringUtils.isEmpty(bairro)
                && StringUtils.isEmpty(cidade)
                && (estado == -1)
                && StringUtils.isEmpty(cep)
                && StringUtils.isEmpty(complemento)
                && StringUtils.isEmpty(telefone)
                && StringUtils.isEmpty(celular)
                ;
    }
}
