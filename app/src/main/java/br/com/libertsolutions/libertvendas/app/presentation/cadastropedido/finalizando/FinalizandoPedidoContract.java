package br.com.libertsolutions.libertvendas.app.presentation.cadastropedido.finalizando;

import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpView;
import java.util.Calendar;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
interface FinalizandoPedidoContract {

    interface View extends MvpView {

        void displayFormasPagamento(List<FormaPagamento> formasPagamento);

        void changeTitle(String newTitle);

        void setViewFields(List<Integer> viewIds);

        void setRequiredFields(List<Integer> requiredViewIds);

        void setViewValue(final int viewId, int position);

        void setViewValue(final int viewId, final String viewValue);

        void showCalendarPicker(Calendar dataPreSelecionada);

        void hideRequiredMessages();

        boolean hasEmptyRequiredFields();

        void displayRequiredFieldMessages();

        String getViewStringValue(final int viewId);

        int getViewPositionValue(final int viewId);

        void displayValidationErrorForDesconto(String validationMessage);

        void resultPedidoEditado(Pedido pedido);

        void finishView();
    }

    interface Presenter extends MvpPresenter<View> {

        void initializeView(Pedido pedidoEmEdicao);

        void handleActionSave();

        void handleDataEmissaoTouched();

        void handleDateSelected(int year, int month, int day);
    }
}
