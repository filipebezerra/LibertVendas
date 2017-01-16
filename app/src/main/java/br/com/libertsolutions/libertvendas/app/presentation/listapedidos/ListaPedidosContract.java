package br.com.libertsolutions.libertvendas.app.presentation.listapedidos;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.MvpView;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
interface ListaPedidosContract {

    interface View extends MvpView {

        void showPedidos(List<Pedido> pedidos);

        void showLoading();

        void showEmptyView();

        void hideLoading();

        void navigateToCadastroPedido(Pedido pedido);

        void updateInsertedItemAtPosition(int position);

        void updateChangedItemAtPosition(int position);

        void navigateToOrderDetail(Pedido order);
    }

    interface Presenter extends MvpPresenter<View> {

        void loadPedidos(boolean showOrdersNotSent);

        void handleItemSelected(int position);

        void handleResultPedidoEditado(Pedido pedidoEditado);

        void refreshOrderList();
    }
}
