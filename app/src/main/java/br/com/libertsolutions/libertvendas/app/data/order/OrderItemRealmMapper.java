package br.com.libertsolutions.libertvendas.app.data.order;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmMapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.OrderItemEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.OrderItem;

import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.priceTableItemMapper;

/**
 * @author Filipe Bezerra
 */
public class OrderItemRealmMapper extends RealmMapper<OrderItem, OrderItemEntity> {

    @Override public OrderItemEntity toEntity(final OrderItem object) {
        return new OrderItemEntity()
                .withId(object.getId())
                .withOrderItemId(object.getOrderItemId())
                .withItem(priceTableItemMapper().toEntity(object.getItem()))
                .withQuantity(object.getQuantity())
                .withSubTotal(object.getSubTotal())
                .withLastChangeTime(object.getLastChangeTime());
    }

    @Override public OrderItem toViewObject(final OrderItemEntity entity) {
        return new OrderItem()
                .withId(entity.getId())
                .withOrderItemId(entity.getOrderItemId())
                .withItem(priceTableItemMapper().toViewObject(entity.getItem()))
                .withQuantity(entity.getQuantity())
                .withSubTotal(entity.getSubTotal())
                .withLastChangeTime(entity.getLastChangeTime());
    }
}
