package br.com.libertsolutions.libertvendas.app.presentation.login;

import br.com.libertsolutions.libertvendas.app.presentation.util.StringUtils;

/**
 * @author Filipe Bezerra
 */
class LoginViewModel {
    String cpfCnpj = null;

    String senha = null;

    LoginViewModel setCpfCnpj(String pCpfCnpj) {
        cpfCnpj = pCpfCnpj;
        return this;
    }

    boolean hasCpfCnpj() {
        return !StringUtils.isEmpty(cpfCnpj);
    }

    LoginViewModel setSenha(String pSenha) {
        senha = pSenha;
        return this;
    }

    boolean hasSenha() {
        return !StringUtils.isEmpty(senha);
    }

    boolean hasDefaultValues() {
        return !hasCpfCnpj() || !hasSenha();
    }
}
