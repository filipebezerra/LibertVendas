package br.com.libertsolutions.libertvendas.app.presentation.dashboard;

import br.com.libertsolutions.libertvendas.app.data.pedido.PedidoRepository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ChartGroupedData;
import br.com.libertsolutions.libertvendas.app.domain.pojo.ItemPedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import br.com.libertsolutions.libertvendas.app.presentation.login.LoggedUserEvent;
import br.com.libertsolutions.libertvendas.app.presentation.mvp.BasePresenter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import rx.Observable;
import rx.Subscriber;
import timber.log.Timber;

import static br.com.libertsolutions.libertvendas.app.PresentationInjection.provideEventBus;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * @author Filipe Bezerra
 */
class DashboardPresenter extends BasePresenter<DashboardContract.View>
        implements DashboardContract.Presenter {

    private final PedidoRepository mOrderRepository;

    private Vendedor mLoggedUser;

    DashboardPresenter(final PedidoRepository orderRepository) {
        mOrderRepository = orderRepository;
    }

    @Subscribe(sticky = true) public void onLoginEvent(LoggedUserEvent event) {
        if (mLoggedUser != null && !mLoggedUser.equals(event.getVendedor())) {
            loadOrdersGrouped();
        }
    }

    @Override public void loadOrdersGrouped() {
        addSubscription(findOrderListAsObservable()
                .observeOn(mainThread())
                .subscribe(
                        new Subscriber<List<Pedido>>() {
                            @Override public void onStart() {
                                getView().hideChart();
                                getView().hideEmpty();
                                getView().showLoading();
                            }

                            @Override public void onError(final Throwable e) {
                                Timber.e(e, "Could not load order list");
                                getView().hideLoading();
                                getView().showError();
                            }

                            @Override public void onNext(final List<Pedido> orderList) {
                                getView().hideLoading();
                                if (!orderList.isEmpty()) {
                                    setOrderGroupedToChart(orderList);
                                } else {
                                    getView().showEmpty();
                                }
                            }

                            @Override public void onCompleted() {}
                        }
                )
        );
    }

    @Override public void retryLoadOrdersGrouped() {
        getView().hideError();
        loadOrdersGrouped();
    }

    private Observable<List<Pedido>> findOrderListAsObservable() {
        mLoggedUser = provideEventBus().getStickyEvent(LoggedUserEvent.class).getVendedor();
        return mOrderRepository.findByVendedorAndEmpresa(
                mLoggedUser.getCpfCnpj(), mLoggedUser.getEmpresaSelecionada().getCnpj());
    }

    private void setOrderGroupedToChart(final List<Pedido> orders) {
        Collections.sort(orders, (first, second) ->
                first.getCliente().getNome().compareTo(second.getCliente().getNome()));

        List<ChartGroupedData> chartData = new ArrayList<>();
        float orderAmount = 0;
        String name = orders.get(0).getCliente().getNome();
        for (Pedido order : orders) {
            if (!name.equals(order.getCliente().getNome())) {
                chartData.add(ChartGroupedData.create(name, orderAmount));
                name = order.getCliente().getNome();
                orderAmount = 0;
            }

            for (ItemPedido item : order.getItens()) {
                orderAmount += item.getSubTotal();
            }
            orderAmount -= order.getDesconto();

            if (orders.indexOf(order) == orders.size() - 1) {
                chartData.add(ChartGroupedData.create(name, orderAmount));
            }
        }

        getView().displayOrdersGrouped(chartData);
    }
}
