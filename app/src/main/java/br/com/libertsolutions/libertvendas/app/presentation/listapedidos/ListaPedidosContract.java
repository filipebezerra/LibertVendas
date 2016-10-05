package br.com.libertsolutions.libertvendas.app.presentation.listapedidos;

import java.util.List;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Produto;

/**
 * @author Filipe Bezerra
 */

interface ListaPedidosContract {
    interface View {
        void showListaPedidos(List<Pedido> pPedidoList);
    }

    interface Presenter {
        void loadListaPedidos();
    }
}
