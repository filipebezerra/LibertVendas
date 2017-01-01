package br.com.libertsolutions.libertvendas.app.domain.pojo;

/**
 * @author Filipe Bezerra
 */
public final class Settings {

    private final String urlServidor;

    private final String chaveAutenticacao;

    private final boolean mSincronizarPedidoAutomaticamente;

    private Settings(final String urlServidor, final String chaveAutenticacao,
            final boolean sincronizarPedidoAutomaticamente) {
        this.urlServidor = urlServidor;
        this.chaveAutenticacao = chaveAutenticacao;
        mSincronizarPedidoAutomaticamente = sincronizarPedidoAutomaticamente;
    }

    public static Settings create(final String urlServidor, final String chaveAutenticacao,
            final boolean sincronizarPedidoAutomaticamente) {
        return new Settings(urlServidor, chaveAutenticacao, sincronizarPedidoAutomaticamente);
    }

    public String getUrlServidor() {
        return urlServidor;
    }

    public String getChaveAutenticacao() {
        return chaveAutenticacao;
    }

    public boolean isSincronizarPedidoAutomaticamente() {
        return mSincronizarPedidoAutomaticamente;
    }
}
