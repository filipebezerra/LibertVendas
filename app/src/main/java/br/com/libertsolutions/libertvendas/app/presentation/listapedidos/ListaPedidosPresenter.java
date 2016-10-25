package br.com.libertsolutions.libertvendas.app.presentation.listapedidos;

import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Filipe Bezerra
 */

class ListaPedidosPresenter implements ListaPedidosContract.Presenter {
    private final ListaPedidosContract.View mView;

    private final Repository<Pedido> mPedidoService;

    ListaPedidosPresenter(
            ListaPedidosContract.View pView,
            Repository<Pedido> pPedidoService) {
        mView = pView;
        mPedidoService = pPedidoService;
    }

    @Override
    public void loadListaPedidos(boolean listaPedidosNaoEnviados) {
        mPedidoService.list()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mView::showListaPedidos);
    }
}
