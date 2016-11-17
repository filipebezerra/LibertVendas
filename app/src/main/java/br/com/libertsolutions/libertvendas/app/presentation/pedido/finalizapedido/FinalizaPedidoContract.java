package br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizapedido;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import java.util.Calendar;

/**
 * @author Filipe Bezerra
 */
interface FinalizaPedidoContract {
    interface View {
        void navigateToListaClientes();

        void showCalendarPicker(Calendar pDataEmissao);

        void hideRequiredMessages();

        void displayRequiredMessageForDataEmissao();

        void displayRequiredMessageForFormaPagamento();

        void displayRequiredMessageForCliente();

        void showFeedbackMessage(String pMessage);

        void resultNovoPedido(Pedido pPedido);
    }

    interface Presenter {
        void initializeView(FinalizaPedidoViewModel pFinalizaPedidoViewModel,
                ProdutosSelecionadosArgumentExtractor pProdutosSelecionadosArgumentExtractor,
                TabelaPrecoPadraoArgumentExtractor pTabelaPrecoPadraoArgumentExtractor);

        void clickActionSave();

        void clickSelectCliente();

        void clickSelectDataEmissao();

        void setDataEmissao(int pYear, int pMonthOfYear, int pDayOfMonth);

        void handleClienteSelecionadoEvent(Cliente pCliente);

        void handleUsuarioLogadoEvent(Vendedor pVendedor);
    }
}
