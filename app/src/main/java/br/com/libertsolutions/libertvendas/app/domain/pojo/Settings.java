package br.com.libertsolutions.libertvendas.app.domain.pojo;

/**
 * @author Filipe Bezerra
 */
public class Settings {

    private final String urlServidor;

    private final String chaveAutenticacao;

    private final boolean sincronizaPedidoAutomaticamente;

    private Settings(
            String pUrlServidor, String pChaveAutenticacao,
            boolean pSincronizaPedidoAutomaticamente) {
        urlServidor = pUrlServidor;
        chaveAutenticacao = pChaveAutenticacao;
        sincronizaPedidoAutomaticamente = pSincronizaPedidoAutomaticamente;
    }

    public static Settings create(
            String pUrlServidor, String pChaveAutenticacao,
            boolean pSincronizaPedidoAutomaticamente) {
        return new Settings(
                pUrlServidor, pChaveAutenticacao, pSincronizaPedidoAutomaticamente
        );
    }

    public String getUrlServidor() {
        return urlServidor;
    }

    public String getChaveAutenticacao() {
        return chaveAutenticacao;
    }

    public boolean isSincronizaPedidoAutomaticamente() {
        return sincronizaPedidoAutomaticamente;
    }
}
