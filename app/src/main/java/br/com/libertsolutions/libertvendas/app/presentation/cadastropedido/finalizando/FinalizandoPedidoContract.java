package br.com.libertsolutions.libertvendas.app.presentation.cadastropedido.finalizando;

import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.base.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.base.MvpView;
import java.util.Calendar;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
interface FinalizandoPedidoContract {

    interface View extends MvpView {

        void displayFormasPagamento(List<FormaPagamento> pFormasPagamentoList);

        void changeTitle(String pNewTitle);

        void setViewFields(List<Integer> pViewIds);

        void setRequiredFields(List<Integer> pRequiredViewIds);

        void setViewValue(final int pViewId, int pPosition);

        void setViewValue(final int pViewId, final String pViewValue);

        void showCalendarPicker(Calendar pDataPreSelecionada);

        void hideRequiredMessages();

        boolean hasEmptyRequiredFields();

        void displayRequiredFieldMessages();

        String getViewStringValue(final int pViewId);

        int getViewPositionValue(final int pViewId);

        void displayValidationErrorForDesconto(String pValidationMessage);

        void finishView();
    }

    interface Presenter extends MvpPresenter<View> {

        void initializeView(Pedido pPedidoFromExtra);

        void handleDataEmissaoTouched();

        void handleDateSelected(int pYear, int pMonthOfYear, int pDayOfMonth);

        void handleActionSave();
    }
}
