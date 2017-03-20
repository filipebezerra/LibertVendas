package br.com.libertsolutions.libertvendas.app.presentation.addorder.orderitems;

import br.com.libertsolutions.libertvendas.app.domain.pojo.OrderItem;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class AddedOrderItemsEvent {

    private final List<OrderItem> mOrderItems;

    private AddedOrderItemsEvent(final List<OrderItem> orderItems) {
        mOrderItems = orderItems;
    }

    static AddedOrderItemsEvent newEvent(final List<OrderItem> orderItems) {
        return new AddedOrderItemsEvent(orderItems);
    }

    public List<OrderItem> getOrderItems() {
        return mOrderItems;
    }
}
