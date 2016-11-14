package br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizapedido;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import java.util.Calendar;

/**
 * @author Filipe Bezerra
 */
interface FinalizaPedidoContract {
    interface View {
        void navigateToListaClientes();

        void showCalendarPicker(Calendar pDataEmissao);

        void resultNovoPedido(Pedido pPedido);
    }

    interface Presenter {
        void initializeView(FinalizaPedidoViewModel pFinalizaPedidoViewModel,
                ProdutosSelecionadosArgumentExtractor pProdutosSelecionadosArgumentExtractor);

        void clickActionSave();

        void clickSelectCliente();

        void clickSelectDataEmissao();

        void setDataEmissao(int pYear, int pMonthOfYear, int pDayOfMonth);

        void handleClienteSelecionadoEvent(Cliente pCliente);
    }
}
