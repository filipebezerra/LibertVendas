package br.com.libertsolutions.libertvendas.app.presentation.orderdetail;

import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemPedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.BasePresenter;

import static br.com.libertsolutions.libertvendas.app.presentation.utils.FormattingUtils.convertMillisecondsToDateAsString;
import static br.com.libertsolutions.libertvendas.app.presentation.utils.FormattingUtils.formatAsDinheiro;

/**
 * @author Filipe Bezerra
 */
class OrderInfoPresenter extends BasePresenter<OrderInfoContract.View>
        implements OrderInfoContract.Presenter {

    @Override public void initializeView(final Pedido order) {
        checkViewAttached();

        final String issueDate = convertMillisecondsToDateAsString(order.getDataEmissao());
        getView().displayIssueDate(issueDate);

        final String customerName = order.getCliente().getNome();
        getView().displayCustomerName(customerName);

        double totalProducts = 0;
        for (ItemPedido orderItem : order.getItens()) {
            totalProducts += orderItem.getSubTotal();
        }
        getView().displayTotalProducts(formatAsDinheiro(totalProducts));

        double discount = order.getDesconto();
        getView().displayDiscount(formatAsDinheiro(discount));

        final FormaPagamento formPayment = order.getFormaPagamento();
        getView().displayFormPayment(formPayment);

        final String note = order.getObservacao();
        getView().displayNote(note);
    }
}
