package br.com.libertsolutions.libertvendas.app.presentation.resources;

/**
 * @author Filipe Bezerra
 */
public interface FinalizaPedidoResourcesRepository extends CommonResourcesRepository {
    String obtainStringMessageVendedorNaoPermitidoAplicarDesconto();

    String obtainStringMessageValorDescontoNaoPermitido();
}
