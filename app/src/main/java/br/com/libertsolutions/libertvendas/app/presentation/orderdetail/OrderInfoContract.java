package br.com.libertsolutions.libertvendas.app.presentation.orderdetail;

import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpView;

/**
 * @author Filipe Bezerra
 */
interface OrderInfoContract {

    interface View extends MvpView {

        void displayIssueDate(String issueDate);

        void displayCustomerName(String customerName);

        void displayTotalProducts(String totalProducts);

        void displayDiscount(String discount);

        void displayFormPayment(FormaPagamento formPayment);

        void displayNote(String note);
    }

    interface Presenter extends MvpPresenter<View> {

        void initializeView(Pedido order);
    }
}
