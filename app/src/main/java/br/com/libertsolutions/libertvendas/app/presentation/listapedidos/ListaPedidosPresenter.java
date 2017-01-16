package br.com.libertsolutions.libertvendas.app.presentation.listapedidos;

import br.com.libertsolutions.libertvendas.app.data.pedido.PedidoRepository;
import br.com.libertsolutions.libertvendas.app.data.sync.SyncOrdersEvent;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.presentation.cadastropedido.finalizando.NovoPedidoEvent;
import br.com.libertsolutions.libertvendas.app.presentation.login.LoggedUserEvent;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.BasePresenter;
import br.com.libertsolutions.libertvendas.app.presentation.utils.ObservableUtils;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import rx.Observable;
import rx.Subscriber;
import timber.log.Timber;

import static java.util.Collections.emptyList;
import static org.greenrobot.eventbus.ThreadMode.BACKGROUND;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * @author Filipe Bezerra
 */
class ListaPedidosPresenter extends BasePresenter<ListaPedidosContract.View>
        implements ListaPedidosContract.Presenter {

    private final PedidoRepository mPedidoRepository;

    private List<Pedido> mPedidos;

    private boolean mIsShowingOrdersNotSent;

    ListaPedidosPresenter(final PedidoRepository pedidoRepository) {
        mPedidoRepository = pedidoRepository;
    }

    @Override public void loadPedidos(final boolean showOrdersNotSent) {
        mIsShowingOrdersNotSent = showOrdersNotSent;
        Observable<List<Pedido>> pedidosFromMemoryCache = ObservableUtils.toObservable(mPedidos);

        LoggedUserEvent event = EventBus.getDefault().getStickyEvent(LoggedUserEvent.class);
        final String cpfCnpjVendedor = event.getVendedor().getCpfCnpj();
        final String cnpjEmpresa = event.getVendedor().getEmpresaSelecionada().getCnpj();

        Observable<List<Pedido>> pedidosFromDiskCache;
        if (showOrdersNotSent) {
            pedidosFromDiskCache = mPedidoRepository
                    .findByStatus(Pedido.STATUS_PENDENTE, cpfCnpjVendedor, cnpjEmpresa)
                    .doOnNext(pedidos -> mPedidos = pedidos);
        } else {
            pedidosFromDiskCache = mPedidoRepository
                    .findByVendedorAndEmpresa(cpfCnpjVendedor, cnpjEmpresa)
                    .doOnNext(pedidos -> mPedidos = pedidos);
        }

        addSubscription(Observable
                .merge(pedidosFromMemoryCache, pedidosFromDiskCache)
                .lastOrDefault(emptyList())
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

    @Override public void refreshOrderList() {
        loadPedidos(mIsShowingOrdersNotSent);
    }

    @Subscribe public void onNewOrderEvent(NovoPedidoEvent event) {
        final Pedido pedido = event.getPedido();
        final int position = mPedidos.size();
        mPedidos.add(pedido);
        getView().updateInsertedItemAtPosition(position);
    }

    @Subscribe(threadMode = BACKGROUND) public void onSyncEvent(SyncOrdersEvent event) {
        loadPedidos(mIsShowingOrdersNotSent);
    }
}
