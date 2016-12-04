package br.com.libertsolutions.libertvendas.app.presentation.resources;

import android.content.res.Resources;
import br.com.libertsolutions.libertvendas.app.R;

/**
 * @author Filipe Bezerra
 */
public class FinalizandoPedidoResourcesRepositoryImpl extends CommonResourcesRepositoryImpl
        implements FinalizandoPedidoResourcesRepository {

    public FinalizandoPedidoResourcesRepositoryImpl(Resources resources) {
        super(resources);
    }

    @Override public String obtainStringMessageVendedorNaoPermitidoAplicarDesconto() {
        return getResources().getString(R.string.message_vendedor_nao_permitido_aplicar_desconto);
    }

    @Override public String obtainStringMessageValorDescontoNaoPermitido() {
        return getResources().getString(R.string.message_valor_desconto_nao_permitido);
    }

    @Override public int obtainDataEmissaoViewId() {
        return R.id.input_layout_data_emissao;
    }

    @Override public int obtainClienteViewId() {
        return R.id.input_layout_cliente;
    }

    @Override public int obtainTotalProdutosViewId() {
        return R.id.input_layout_total_produtos;
    }

    @Override public int obtainDescontoViewId() {
        return R.id.input_layout_desconto;
    }

    @Override public int obtainFormaPagamentoViewId() {
        return R.id.spinner_forma_pagamento;
    }

    @Override public int obtainObservacaoViewId() {
        return R.id.input_layout_observacao;
    }
}
