package br.com.libertsolutions.libertvendas.app.data.produtos;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * @author Filipe Bezerra
 */
public class ProdutoRepositories {
    private static ProdutoService sService = null;

    public synchronized static ProdutoService getService(@NonNull Context context) {
        if (sService == null) {
            sService = new MockProdutoServiceImpl();
        }
        return sService;
    }
}
