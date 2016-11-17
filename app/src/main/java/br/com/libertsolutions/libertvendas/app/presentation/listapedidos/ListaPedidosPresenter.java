package br.com.libertsolutions.libertvendas.app.presentation.listapedidos;

import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.util.ObservableUtils;
import java.util.Collections;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

/**
 * @author Filipe Bezerra
 */

class ListaPedidosPresenter implements ListaPedidosContract.Presenter {
    private final ListaPedidosContract.View mView;

    private final Repository<Pedido> mPedidoRepository;

    private List<Pedido> mPedidoList;

    ListaPedidosPresenter(
            ListaPedidosContract.View pView, Repository<Pedido> pPedidoRepository) {
        mView = pView;
        mPedidoRepository = pPedidoRepository;
    }

    @Override public void loadListaPedidos(boolean listaPedidosNaoEnviados) {
        Observable<List<Pedido>> pedidosFromMemoryCache = ObservableUtils
                .toObservable(mPedidoList);

        Observable<List<Pedido>> pedidosFromDiskCache = mPedidoRepository
                .list()
                .doOnNext(this::savePedidosToMemoryCache);

        Observable
                .concat(pedidosFromMemoryCache, pedidosFromDiskCache)
                .firstOrDefault(Collections.emptyList())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Pedido>>() {
                    @Override public void onStart() {
                        mView.showLoading();
                    }

                    @Override public void onError(Throwable e) {
                        Timber.e(e);
                        mView.hideLoading();
                    }

                    @Override public void onNext(List<Pedido> pPedidos) {
                        mView.showListaPedidos(mPedidoList);
                    }

                    @Override public void onCompleted() {}
                });
    }

    private void savePedidosToMemoryCache(List<Pedido> pPedidoList) {
        mPedidoList = pPedidoList;
    }

    @Override public void addNewPedidoCadastrado(Pedido pPedido) {
        final int lastPosition = mPedidoList.size();
        mPedidoList.add(pPedido);
        mView.updateListaPedidos(lastPosition);
    }
}
