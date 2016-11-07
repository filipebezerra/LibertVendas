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

    int cidade = -1;

    int estado = -1;

    String cep = null;

    String complemento = null;

    String telefone = null;

    String celular = null;

    ClienteViewModel setTipoPessoa(int pTipoPessoa) {
        tipoPessoa = pTipoPessoa;
        return this;
    }

    boolean hasTipoPessoa() {
        return tipoPessoa != -1;
    }

    ClienteViewModel setCpfCnpj(String pCpfCnpj) {
        cpfCnpj = pCpfCnpj;
        return this;
    }

    boolean hasCpfCnpj() {
        return !StringUtils.isEmpty(cpfCnpj);
    }

    ClienteViewModel setNome(String pNome) {
        nome = pNome;
        return this;
    }

    boolean hasNome() {
        return !StringUtils.isEmpty(nome);
    }

    ClienteViewModel setEmail(String pEmail) {
        email = pEmail;
        return this;
    }

    boolean hasEmail() {
        return !StringUtils.isEmpty(email);
    }

    ClienteViewModel setEndereco(String pEndereco) {
        endereco = pEndereco;
        return this;
    }

    boolean hasEndereco() {
        return !StringUtils.isEmpty(endereco);
    }

    ClienteViewModel setNumero(String pNumero) {
        numero = pNumero;
        return this;
    }

    boolean hasNumero() {
        return !StringUtils.isEmpty(numero);
    }

    ClienteViewModel setBairro(String pBairro) {
        bairro = pBairro;
        return this;
    }

    boolean hasBairro() {
        return !StringUtils.isEmpty(bairro);
    }

    ClienteViewModel setCidade(int pCidade) {
        cidade = pCidade;
        return this;
    }

    boolean hasCidade() {
        return cidade != -1;
    }

    ClienteViewModel setEstado(int pEstado) {
        estado = pEstado;
        return this;
    }

    boolean hasEstado() {
        return estado != -1;
    }

    ClienteViewModel setCep(String pCep) {
        cep = pCep;
        return this;
    }

    boolean hasCep() {
        return !StringUtils.isEmpty(cep);
    }

    ClienteViewModel setComplemento(String pComplemento) {
        complemento = pComplemento;
        return this;
    }

    boolean hasComplemento() {
        return !StringUtils.isEmpty(complemento);
    }

    ClienteViewModel setTelefone(String pTelefone) {
        telefone = pTelefone;
        return this;
    }

    boolean hasTelefone() {
        return !StringUtils.isEmpty(telefone);
    }

    ClienteViewModel setCelular(String pCelular) {
        celular = pCelular;
        return this;
    }

    boolean hasCelular() {
        return !StringUtils.isEmpty(celular);
    }

    boolean hasDefaultValues() {
        return !hasTipoPessoa()
                || !hasCpfCnpj()
                || !hasNome()
                || !hasEmail()
                || !hasEndereco()
                || !hasNumero()
                || !hasBairro()
                || !hasCidade()
                || !hasEstado()
                || !hasCep()
                || !hasComplemento()
                || !hasTelefone()
                || !hasCelular()
                ;
    }

    boolean hasRequiredValues() {
        return hasTipoPessoa()
                && hasCpfCnpj()
                && hasNome()
                && hasEstado()
                && hasCidade();

    }
}
