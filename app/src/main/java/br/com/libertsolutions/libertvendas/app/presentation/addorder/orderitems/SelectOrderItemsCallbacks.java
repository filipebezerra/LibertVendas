package br.com.libertsolutions.libertvendas.app.presentation.addorder.orderitems;

import br.com.libertsolutions.libertvendas.app.domain.pojo.OrderItem;

/**
 * @author Filipe Bezerra
 */
interface SelectOrderItemsCallbacks {

    void onAddOrderItemRequested(final OrderItem orderItem, final int position);

    void onRemoveOrderItemRequested(final OrderItem orderItem, final int position);

    void onChangeOrderItemQuantityRequested(
            final OrderItem orderItem, final float quantity, final int position);
}
