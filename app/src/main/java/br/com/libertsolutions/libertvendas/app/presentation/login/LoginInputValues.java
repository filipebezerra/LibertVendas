package br.com.libertsolutions.libertvendas.app.presentation.login;

import br.com.libertsolutions.libertvendas.app.presentation.utils.StringUtils;

/**
 * @author Filipe Bezerra
 */
class LoginInputValues {

    String cpfCnpj = "";

    String senha = "";

    LoginInputValues setCpfCnpj(final String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
        return this;
    }

    boolean hasCpfCnpj() {
        return !StringUtils.isNullOrEmpty(cpfCnpj);
    }

    LoginInputValues setSenha(final String senha) {
        this.senha = senha;
        return this;
    }

    boolean hasSenha() {
        return !StringUtils.isNullOrEmpty(senha);
    }

    boolean hasDefaultValues() {
        return !hasCpfCnpj() || !hasSenha();
    }
}
