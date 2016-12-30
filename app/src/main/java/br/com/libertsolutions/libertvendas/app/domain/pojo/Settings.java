package br.com.libertsolutions.libertvendas.app.domain.pojo;

/**
 * @author Filipe Bezerra
 */
public final class Settings {

    private final String urlServidor;

    private final String chaveAutenticao;

    private Settings(final String urlServidor, final String chaveAutenticao) {
        this.urlServidor = urlServidor;
        this.chaveAutenticao = chaveAutenticao;
    }

    public static Settings create(final String urlServidor, final String chaveAutenticao) {
        return new Settings(urlServidor, chaveAutenticao);
    }

    public String getUrlServidor() {
        return urlServidor;
    }

    public String getChaveAutenticao() {
        return chaveAutenticao;
    }
}
