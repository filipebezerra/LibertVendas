package br.com.libertsolutions.libertvendas.app.presentation.orderlist;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Order;

/**
 * @author Filipe Bezerra
 */
public class SelectedOrderEvent {

    private final Order mOrder;

    private final boolean mDuplicateOrder;

    private SelectedOrderEvent(final Order order, final boolean duplicateOrder) {
        mOrder = order;
        mDuplicateOrder = duplicateOrder;
    }

    public static SelectedOrderEvent selectOrder(final Order order) {
        return new SelectedOrderEvent(order, false);
    }

    public static SelectedOrderEvent duplicateOrder(final Order order) {
        return new SelectedOrderEvent(order, true);
    }

    public Order getOrder() {
        return mOrder;
    }

    public boolean isDuplicateOrder() {
        return mDuplicateOrder;
    }
}
