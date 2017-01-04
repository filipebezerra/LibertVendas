package br.com.libertsolutions.libertvendas.app.presentation.resources;

/**
 * @author Filipe Bezerra
 */
public interface FinalizandoPedidoResourcesRepository extends CommonResourcesRepository {

    String obtainStringMessageVendedorNaoPermitidoAplicarDesconto();

    String obtainStringMessageValorDescontoNaoPermitido();

    int obtainDataEmissaoViewId();

    int obtainClienteViewId();

    int obtainTotalProdutosViewId();

    int obtainDescontoViewId();

    int obtainFormaPagamentoViewId();

    int obtainObservacaoViewId();

    String obtainStringTitleEditandoPedido();
}
