package br.com.libertsolutions.libertvendas.app.presentation.orderlist;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Order;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class SyncOrdersEvent {

    private final List<Order> orders;

    private SyncOrdersEvent(final List<Order> orders) {
        this.orders = orders;
    }

    static SyncOrdersEvent just(final List<Order> orders) {
        return new SyncOrdersEvent(orders);
    }

    public List<Order> getOrders() {
        return orders;
    }
}
