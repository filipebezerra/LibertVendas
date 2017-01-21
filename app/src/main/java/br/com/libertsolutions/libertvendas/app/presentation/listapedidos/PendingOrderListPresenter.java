package br.com.libertsolutions.libertvendas.app.presentation.listapedidos;

import br.com.libertsolutions.libertvendas.app.data.pedido.PedidoRepository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import java.util.List;
import rx.Observable;

/**
 * @author Filipe Bezerra
 */
class PendingOrderListPresenter extends BaseOrderListPresenter {

    PendingOrderListPresenter(final PedidoRepository orderRepository) {
        super(orderRepository);
    }

    @Override protected Observable<List<Pedido>> findOrderListAsObservable() {
        return mOrderRepository
                .findByStatus(
                        Pedido.STATUS_PENDENTE,
                        mLoggedUser.getCpfCnpj(),
                        mLoggedUser.getEmpresaSelecionada().getCnpj());
    }

    @Override protected boolean isStatusIndicatorVisible() {
        return false;
    }
}
