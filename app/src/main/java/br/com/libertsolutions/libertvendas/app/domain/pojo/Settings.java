package br.com.libertsolutions.libertvendas.app.domain.pojo;

/**
 * @author Filipe Bezerra
 */
public class Settings {
    private final String urlServidor;

    private final boolean sincronizarPedidoAutomaticamente;

    private final boolean podeAplicatDesconto;

    private Settings(
            String urlServidor, boolean sincronizarPedidoAutomaticamente,
            boolean podeAplicatDesconto) {
        this.urlServidor = urlServidor;
        this.sincronizarPedidoAutomaticamente = sincronizarPedidoAutomaticamente;
        this.podeAplicatDesconto = podeAplicatDesconto;
    }

    public static Settings create(
            String urlServidor, boolean sincronizarPedidoAutomaticamente,
            boolean podeAplicatDesconto) {
        return new Settings(urlServidor, sincronizarPedidoAutomaticamente, podeAplicatDesconto);
    }

    public String getUrlServidor() {
        return urlServidor;
    }

    public boolean isSincronizarPedidoAutomaticamente() {
        return sincronizarPedidoAutomaticamente;
    }

    public boolean isPodeAplicatDesconto() {
        return podeAplicatDesconto;
    }
}
