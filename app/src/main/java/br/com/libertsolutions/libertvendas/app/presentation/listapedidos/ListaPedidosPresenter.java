package br.com.libertsolutions.libertvendas.app.presentation.listapedidos;

import br.com.libertsolutions.libertvendas.app.data.pedido.PedidoRepository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.cadastropedido.finalizando.NovoPedidoEvent;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.BasePresenter;
import br.com.libertsolutions.libertvendas.app.presentation.utils.ObservableUtils;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import rx.Observable;
import rx.Subscriber;
import timber.log.Timber;

import static java.util.Collections.emptyList;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * @author Filipe Bezerra
 */
class ListaPedidosPresenter extends BasePresenter<ListaPedidosContract.View>
        implements ListaPedidosContract.Presenter {

    private final PedidoRepository mPedidoRepository;

    private List<Pedido> mPedidos;

    ListaPedidosPresenter(final PedidoRepository pedidoRepository) {
        mPedidoRepository = pedidoRepository;
    }

    @Override public void loadPedidos(final boolean showOrdersNotSent) {
        Observable<List<Pedido>> pedidosFromMemoryCache = ObservableUtils.toObservable(mPedidos);

        Observable<List<Pedido>> pedidosFromDiskCache;
        if (showOrdersNotSent) {
            pedidosFromDiskCache = mPedidoRepository
                    .findByStatus(Pedido.STATUS_PENDENTE)
                    .doOnNext(pedidos -> mPedidos = pedidos);
        } else {
            pedidosFromDiskCache = mPedidoRepository
                    .findAll()
                    .doOnNext(pedidos -> mPedidos = pedidos);
        }

        addSubscription(Observable
                .concat(pedidosFromMemoryCache, pedidosFromDiskCache)
                .firstOrDefault(emptyList())
                .observeOn(mainThread())
                .subscribe(new Subscriber<List<Pedido>>() {
                    @Override public void onStart() {
                        getView().showLoading();
                    }

                    @Override public void onError(Throwable e) {
                        Timber.e(e, "Could not load orders list from local database");
                        getView().hideLoading();
                    }

                    @Override public void onNext(List<Pedido> pPedidos) {
                        getView().showPedidos(mPedidos);
                    }

                    @Override public void onCompleted() {}
                }));
    }

    @Override public void handleItemSelected(final int position) {
        if (position >= 0 && position < mPedidos.size()) {
            final Pedido order = mPedidos.get(position);

            if (order.getStatus() == Pedido.STATUS_PENDENTE) {
                getView().navigateToCadastroPedido(order);
            } else {
                getView().navigateToOrderDetail(order);
            }
        }
    }

    @Override public void handleResultPedidoEditado(final Pedido pedidoEditado) {
        int position = mPedidos.indexOf(pedidoEditado);
        if (position != -1) {
            mPedidos.set(position, pedidoEditado);
            getView().updateChangedItemAtPosition(position);
        }
    }

    @Subscribe public void onNewOrderEvent(NovoPedidoEvent event) {
        final Pedido pedido = event.getPedido();
        final int position = mPedidos.size();
        mPedidos.add(pedido);
        getView().updateInsertedItemAtPosition(position);
    }
}
