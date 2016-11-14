package br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizapedido;

import java.util.Calendar;

/**
 * @author Filipe Bezerra
 */
interface FinalizaPedidoContract {
    interface View {
        void navigateToListaClientesActivity();

        void showCalendarPicker(Calendar pDataEmissao);
    }

    interface Presenter {
        void initializeView(FinalizaPedidoViewModel pFinalizaPedidoViewModel,
                ProdutosSelecionadosArgumentExtractor pProdutosSelecionadosArgumentExtractor);

        void clickActionSave();

        void clickSelectCliente();

        void clickSelectDataEmissao();

        void setDataEmissao(int pYear, int pMonthOfYear, int pDayOfMonth);
    }
}
