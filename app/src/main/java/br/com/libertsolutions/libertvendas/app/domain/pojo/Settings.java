package br.com.libertsolutions.libertvendas.app.domain.pojo;

/**
 * @author Filipe Bezerra
 */
public class Settings {
    private final String urlServidor;

    private final String chaveAutenticacao;

    private final boolean sincronizaPedidoAutomaticamente;

    private final boolean podeAplicarDesconto;

    private final int tabelaPrecoPadrao;

    private Settings(
            String pUrlServidor, String pChaveAutenticacao, boolean pSincronizaPedidoAutomaticamente,
            boolean pPodeAplicarDesconto, int pTabelaPrecoPadrao) {
        urlServidor = pUrlServidor;
        chaveAutenticacao = pChaveAutenticacao;
        sincronizaPedidoAutomaticamente = pSincronizaPedidoAutomaticamente;
        podeAplicarDesconto = pPodeAplicarDesconto;
        tabelaPrecoPadrao = pTabelaPrecoPadrao;
    }

    public static Settings create(
            String pUrlServidor, String pChaveAutenticacao, boolean pSincronizaPedidoAutomaticamente,
            boolean pPodeAplicarDesconto, int pTabelaPrecoPadrao) {
        return new Settings(
                pUrlServidor, pChaveAutenticacao, pSincronizaPedidoAutomaticamente,
                pPodeAplicarDesconto, pTabelaPrecoPadrao);
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

    public boolean isPodeAplicarDesconto() {
        return podeAplicarDesconto;
    }

    public int getTabelaPrecoPadrao() {
        return tabelaPrecoPadrao;
    }
}
