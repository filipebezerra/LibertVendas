package br.com.libertsolutions.libertvendas.app.presentation.listapedidos;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import java.util.List;

/**
 * @author Filipe Bezerra
 */

interface ListaPedidosContract {
    interface View {
        void showListaPedidos(List<Pedido> pPedidoList);

        void showLoading();

        void hideLoading();

        void updateListaPedidos(int pPosition);
    }

    interface Presenter {
        void loadListaPedidos(boolean listaPedidosNaoEnviados);

        void addNewPedidoCadastrado(Pedido pPedido);
    }
}
