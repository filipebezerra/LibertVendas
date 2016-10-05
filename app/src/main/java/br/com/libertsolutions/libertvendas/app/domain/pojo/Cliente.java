package br.com.libertsolutions.libertvendas.app.domain.pojo;

/**
 * @author Filipe Bezerra
 */

public class Cliente {
    private final String razaoSocial;

    private final String telefone;

    private final String email;

    public Cliente(String pRazaoSocial, String pTelefone, String pEmail) {
        razaoSocial = pRazaoSocial;
        telefone = pTelefone;
        email = pEmail;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }
}
