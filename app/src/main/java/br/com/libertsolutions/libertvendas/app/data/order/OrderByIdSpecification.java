package br.com.libertsolutions.libertvendas.app.data.order;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmSingleSpecification;
import br.com.libertsolutions.libertvendas.app.domain.entity.OrderEntity;
import io.realm.Realm;

import static br.com.libertsolutions.libertvendas.app.domain.entity.OrderEntity.Fields.ORDER_ID;

/**
 * @author Filipe Bezerra
 */
public class OrderByIdSpecification implements RealmSingleSpecification<OrderEntity> {

    private final int orderId;

    public OrderByIdSpecification(final int orderId) {
        this.orderId = orderId;
    }

    @Override public OrderEntity toSingle(final Realm realm) {
        return realm.where(OrderEntity.class)
                .equalTo(ORDER_ID, orderId)
                .findFirst();
    }
}
