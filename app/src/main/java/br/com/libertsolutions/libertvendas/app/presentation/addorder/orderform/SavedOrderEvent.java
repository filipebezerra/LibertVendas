package br.com.libertsolutions.libertvendas.app.presentation.addorder.orderform;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Order;

/**
 * @author Filipe Bezerra
 */
public class SavedOrderEvent {

    private final Order mOrder;

    private SavedOrderEvent(final Order order) {
        mOrder = order;
    }

    static SavedOrderEvent newEvent(final Order order) {
        return new SavedOrderEvent(order);
    }

    public Order getOrder() {
        return mOrder;
    }
}
