package br.com.libertsolutions.libertvendas.app.data.order;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmMapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.OrderEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.OrderItemEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Order;
import io.realm.RealmList;

import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.customerMapper;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.orderItemMapper;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.paymentMethodMapper;
import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.priceTableMapper;

/**
 * @author Filipe Bezerra
 */
public class OrderRealmMapper extends RealmMapper<Order, OrderEntity> {
    @Override public OrderEntity toEntity(final Order object) {
        return new OrderEntity()
                .withId(object.getId())
                .withOrderId(object.getOrderId())
                .withType(object.getType())
                .withIssueDate(object.getIssueDate())
                .withDiscount(object.getDiscount())
                .withObservation(object.getObservation())
                .withCustomer(customerMapper().toEntity(object.getCustomer()))
                .withPaymentMethod(paymentMethodMapper().toEntity(object.getPaymentMethod()))
                .withPriceTable(priceTableMapper().toEntity(object.getPriceTable()))
                .withItems((RealmList<OrderItemEntity>) orderItemMapper()
                        .toEntities(object.getItems()))
                .withLastChangeTime(object.getLastChangeTime())
                .withSalesmanId(object.getSalesmanId())
                .withCompanyId(object.getCompanyId())
                .withStatus(object.getStatus());
    }

    @Override public Order toViewObject(final OrderEntity entity) {
        return new Order()
                .withId(entity.getId())
                .withOrderId(entity.getOrderId())
                .withType(entity.getType())
                .withIssueDate(entity.getIssueDate())
                .withDiscount(entity.getDiscount())
                .withObservation(entity.getObservation())
                .withCustomer(customerMapper().toViewObject(entity.getCustomer()))
                .withPaymentMethod(paymentMethodMapper().toViewObject(entity.getPaymentMethod()))
                .withPriceTable(priceTableMapper().toViewObject(entity.getPriceTable()))
                .withItems(orderItemMapper().toViewObjects(entity.getItems()))
                .withLastChangeTime(entity.getLastChangeTime())
                .withSalesmanId(entity.getSalesmanId())
                .withCompanyId(entity.getCompanyId())
                .withStatus(entity.getStatus());
    }
}
