package br.com.libertsolutions.libertvendas.app.presentation.listapedidos;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpView;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
interface OrderListContract {

    interface View extends MvpView {

        void showLoading();

        void hideLoading();

        void showErrorLoadingOrderList();

        boolean isDisplayingEmptyView();

        void showEmptyView();

        void hideEmptyView();

        void displayOrderList(final List<Pedido> orderList, final boolean statusIndicatorVisible);

        void refreshOrderList(final List<Pedido> orderList);

        void navigateToOrderEditor(final Pedido selectedOrder);

        void navigateToOrderDetail(final Pedido selectedOrder);

        void updateEditedOrderAtPosition(final int position);

        boolean isDisplayingOrderList();

        void updateAddedOrderAtPosition(final int position);
    }

    interface Presenter extends MvpPresenter<View> {

        void loadOrderList();

        void viewOrder(int position);

        void handleResultFromOrderEditor(int requestCode, int resultCode, Pedido editedOrder);

        void handleDisplayingOrderListDone();
    }
}
