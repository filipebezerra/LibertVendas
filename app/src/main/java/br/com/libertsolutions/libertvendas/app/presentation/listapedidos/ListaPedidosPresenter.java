package br.com.libertsolutions.libertvendas.app.presentation.listapedidos;

import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.base.BasePresenter;
import br.com.libertsolutions.libertvendas.app.presentation.cadastropedido.finalizando.NovoPedidoEvent;
import br.com.libertsolutions.libertvendas.app.presentation.util.ObservableUtils;
import java.util.Collections;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

import static org.greenrobot.eventbus.ThreadMode.MAIN;

/**
 * @author Filipe Bezerra
 */

class ListaPedidosPresenter extends BasePresenter<ListaPedidosContract.View>
        implements ListaPedidosContract.Presenter {

    private final Repository<Pedido> mPedidoRepository;

    private List<Pedido> mPedidoList;

    ListaPedidosPresenter(Repository<Pedido> pPedidoRepository) {
        mPedidoRepository = pPedidoRepository;
    }

    @Override public void loadListaPedidos(boolean listaPedidosNaoEnviados) {
        Observable<List<Pedido>> pedidosFromMemoryCache = ObservableUtils
                .toObservable(mPedidoList);

        Observable<List<Pedido>> pedidosFromDiskCache = mPedidoRepository
                .list()
                .doOnNext(this::savePedidosToMemoryCache);

        addSubscription(Observable
                .concat(pedidosFromMemoryCache, pedidosFromDiskCache)
                .firstOrDefault(Collections.emptyList())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Pedido>>() {
                    @Override public void onStart() {
                        getView().showLoading();
                    }

                    @Override public void onError(Throwable e) {
                        Timber.e(e);
                        getView().hideLoading();
                    }

                    @Override public void onNext(List<Pedido> pPedidos) {
                        getView().showListaPedidos(mPedidoList);
                    }

                    @Override public void onCompleted() {}
                }));
    }

    private void savePedidosToMemoryCache(List<Pedido> pPedidoList) {
        mPedidoList = pPedidoList;
    }

    @Subscribe(threadMode = MAIN) public void onPedidoSavedEvent(NovoPedidoEvent pEvent) {
        final Pedido pedido = pEvent.getEventValue();
        final int position = mPedidoList.size();
        mPedidoList.add(pedido);
        getView().updateInsertedItemAtPosition(position);
    }
}
