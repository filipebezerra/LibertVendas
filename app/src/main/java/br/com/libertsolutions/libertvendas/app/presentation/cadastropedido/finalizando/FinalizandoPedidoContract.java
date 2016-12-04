package br.com.libertsolutions.libertvendas.app.presentation.cadastropedido.finalizando;

import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.base.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.base.MvpView;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
interface FinalizandoPedidoContract {

    interface View extends MvpView {

        void displayFormasPagamento(List<FormaPagamento> pFormasPagamentoList);

        void setViewFields(List<Integer> pViewIds);

        void setRequiredFields(List<Integer> pRequiredViewIds);

        void setViewValue(final int pViewId, int pPosition);
    }

    interface Presenter extends MvpPresenter<View> {

        void initializeView(Pedido pPedidoFromExtra);
    }
}
