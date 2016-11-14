package br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizapedido;

import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import java.util.Calendar;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
interface FinalizaPedidoContract {
    interface View {
        void bindFormasPagamento(List<FormaPagamento> pFormaPagamentoList);

        void navigateToListaClientesActivity();

        void showCalendarPicker(Calendar pDataEmissao);

        void bindDataEmissao(String pDataEmissao);
    }

    interface Presenter {
        void initializeView();

        void clickActionSave();

        void clickSelectCliente();

        void clickSelectDataEmissao();

        void setDataEmissao(int pYear, int pMonthOfYear, int pDayOfMonth);
    }
}
