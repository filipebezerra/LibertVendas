package br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizando;

import br.com.libertsolutions.libertvendas.app.presentation.base.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.base.MvpView;
import java.util.Calendar;

/**
 * @author Filipe Bezerra
 */
interface FinalizandoPedidoContract {

    interface View extends MvpView {

        void showCalendarPicker(Calendar pDataEmissao);

        void hideRequiredMessages();

        void displayRequiredMessageForDataEmissao();

        void displayRequiredMessageForFormaPagamento();

        void displayRequiredMessageForCliente();

        void displayValidationErrorForDesconto(String pMessage);

        void showFeedbackMessage(String pMessage);
    }

    interface Presenter extends MvpPresenter<View> {

        void attachViewModel(
                FinalizandoPedidoViewModel pViewModel,
                FinalizandoPedidoExtrasExtractor pExtrasExtractor);

        void clickSelectDataEmissao();

        void changeDataEmissao(long pDataSelecionada);

        void clickActionSave();

    }

}
