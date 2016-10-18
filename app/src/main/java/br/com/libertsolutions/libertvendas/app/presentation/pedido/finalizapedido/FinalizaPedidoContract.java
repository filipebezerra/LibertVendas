package br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizapedido;

import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
interface FinalizaPedidoContract {
    interface View {
        void bindFormasPagamento(List<FormaPagamento> pFormaPagamentoList);
    }

    interface Presenter {
        void initializeView();

        void clickActionSave();
    }
}
