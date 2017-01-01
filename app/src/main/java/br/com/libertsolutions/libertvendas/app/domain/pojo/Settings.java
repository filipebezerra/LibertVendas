package br.com.libertsolutions.libertvendas.app.domain.pojo;

/**
 * @author Filipe Bezerra
 */
public final class Settings {

    private final String urlServidor;

    private final String chaveAutenticacao;

    private Settings(final String urlServidor, final String chaveAutenticacao) {
        this.urlServidor = urlServidor;
        this.chaveAutenticacao = chaveAutenticacao;
    }

    public static Settings create(final String urlServidor, final String chaveAutenticacao) {
        return new Settings(urlServidor, chaveAutenticacao);
    }

    public String getUrlServidor() {
        return urlServidor;
    }

    public String getChaveAutenticacao() {
        return chaveAutenticacao;
    }
}
