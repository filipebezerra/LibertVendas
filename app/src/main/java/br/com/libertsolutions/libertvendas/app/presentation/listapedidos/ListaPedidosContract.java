package br.com.libertsolutions.libertvendas.app.presentation.listapedidos;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.base.MvpPresenter;
import br.com.libertsolutions.libertvendas.app.presentation.base.MvpView;
import java.util.List;

/**
 * @author Filipe Bezerra
 */

interface ListaPedidosContract {

    interface View extends MvpView {

        void showListaPedidos(List<Pedido> pPedidoList);

        void showLoading();

        void hideLoading();

        void navigateToCadastroPedido(Pedido pPedido);

        void updateInsertedItemAtPosition(int pPosition);

        void updateChangedItemAtPosition(int pPosition);
    }

    interface Presenter extends MvpPresenter<View> {

        void loadListaPedidos(boolean listaPedidosNaoEnviados);

        void handleSingleTapUp(int pPosition);

        void handleResultPedidoEditado(Pedido pPedidoEditado);
    }
}
