package br.com.libertsolutions.libertvendas.app.presentation.listapedidos;

import br.com.libertsolutions.libertvendas.app.data.pedidos.PedidoService;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Filipe Bezerra
 */

class ListaPedidosPresenter implements ListaPedidosContract.Presenter {
    private final ListaPedidosContract.View mView;
    private final PedidoService mPedidoService;

    ListaPedidosPresenter(
            ListaPedidosContract.View pView, PedidoService pPedidoService) {
        mView = pView;
        mPedidoService = pPedidoService;
    }

    @Override
    public void loadListaPedidos(boolean listaPedidosNaoEnviados) {
        mPedidoService.get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mView::showListaPedidos);
    }
}
