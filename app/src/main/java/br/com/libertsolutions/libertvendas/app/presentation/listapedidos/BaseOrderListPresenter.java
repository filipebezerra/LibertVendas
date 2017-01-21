package br.com.libertsolutions.libertvendas.app.presentation.listapedidos;

import br.com.libertsolutions.libertvendas.app.data.pedido.PedidoRepository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import br.com.libertsolutions.libertvendas.app.presentation.activity.Navigator;
import br.com.libertsolutions.libertvendas.app.presentation.cadastropedido.finalizando.NovoPedidoEvent;
import br.com.libertsolutions.libertvendas.app.presentation.login.LoggedUserEvent;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.BasePresenter;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import rx.Observable;
import rx.Subscriber;
import timber.log.Timber;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * @author Filipe Bezerra
 */
abstract class BaseOrderListPresenter extends BasePresenter<OrderListContract.View>
        implements OrderListContract.Presenter {

    final PedidoRepository mOrderRepository;

    final List<Pedido> mOrderList = new ArrayList<>();

    Vendedor mLoggedUser;

    BaseOrderListPresenter(final PedidoRepository orderRepository) {
        mOrderRepository = orderRepository;
    }

    @Subscribe(sticky = true) public void onLoginEvent(LoggedUserEvent event) {
        if (mLoggedUser != null && !mLoggedUser.equals(event.getVendedor())) {
            loadOrderList();
        }
        mLoggedUser = event.getVendedor();
    }

    @Override public void loadOrderList() {
        addSubscription(findOrderListAsObservable()
                .observeOn(mainThread())
                .subscribe(
                        new Subscriber<List<Pedido>>() {
                            @Override public void onStart() {
                                getView().showLoading();
                            }

                            @Override public void onError(final Throwable e) {
                                Timber.e(e, "Could not load order list");
                                getView().hideLoading();
                                getView().showErrorLoadingOrderList();
                            }

                            @Override public void onNext(final List<Pedido> orderList) {
                                if (!orderList.isEmpty()) {
                                    setOrderListToView(orderList);
                                } else {
                                    setEmptyView();
                                }
                            }

                            @Override public void onCompleted() {}
                        }
                )
        );
    }

    protected abstract Observable<List<Pedido>> findOrderListAsObservable();

    private void setOrderListToView(final List<Pedido> orderList) {
        if (getView().isDisplayingEmptyView()) {
            getView().hideEmptyView();
        }

        if (mOrderList.isEmpty()) {
            mOrderList.addAll(orderList);
            getView().displayOrderList(mOrderList, isStatusIndicatorVisible());
        } else {
            //TODO: Fix: ao atualizar após a sincronização as novas informações não estão presentes
            getView().hideLoading();
            getView().refreshOrderList(orderList);
        }
    }

    protected abstract boolean isStatusIndicatorVisible();

    private void setEmptyView() {
        getView().showEmptyView();
        getView().hideLoading();
    }

    @Override public void viewOrder(final int position) {
        if (position >= 0 && position < mOrderList.size()) {
            Pedido selectedOrder = mOrderList.get(position);

            if (selectedOrder.getStatus() == Pedido.STATUS_PENDENTE) {
                getView().navigateToOrderEditor(selectedOrder);
            } else {
                getView().navigateToOrderDetail(selectedOrder);
            }
        }
    }

    @Override public void handleResultFromOrderEditor(
            final int requestCode, final int resultCode, final Pedido editedOrder) {
        if (requestCode == Navigator.REQUEST_EDITAR_PEDIDO && resultCode == Navigator.RESULT_OK) {
            int position = mOrderList.indexOf(editedOrder);
            if (position != -1) {
                mOrderList.set(position, editedOrder);
                getView().updateEditedOrderAtPosition(position);
            }
        }
    }

    @Override public void handleDisplayingOrderListDone() {
        getView().hideLoading();
    }

    @Subscribe public void onNewOrderEvent(NovoPedidoEvent event) {
        mOrderList.add(event.getPedido());

        if (getView().isDisplayingOrderList()) {
            getView().updateAddedOrderAtPosition(mOrderList.size() - 1);
        } else {
            if (getView().isDisplayingEmptyView()) {
                getView().hideEmptyView();
            }

            //TODO: Fix: visualizar o novo item adicionado à lista
            getView().displayOrderList(mOrderList, isStatusIndicatorVisible());
        }
    }

    @Subscribe(sticky = true) public void onUserLogin(LoggedUserEvent event) {
        loadOrderList();
    }
}
