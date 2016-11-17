package br.com.libertsolutions.libertvendas.app.presentation.resources;

import android.content.res.Resources;
import br.com.libertsolutions.libertvendas.app.R;

/**
 * @author Filipe Bezerra
 */
public class FinalizaPedidoResourcesRepositoryImpl extends CommonResourcesRepositoryImpl
        implements FinalizaPedidoResourcesRepository {
    public FinalizaPedidoResourcesRepositoryImpl(Resources resources) {
        super(resources);
    }

    @Override public String obtainStringMessageVendedorNaoPermitidoAplicarDesconto() {
        return getResources().getString(R.string.message_vendedor_nao_permitido_aplicar_desconto);
    }

    @Override public String obtainStringMessageValorDescontoNaoPermitido() {
        return getResources().getString(R.string.message_valor_desconto_nao_permitido);
    }
}
