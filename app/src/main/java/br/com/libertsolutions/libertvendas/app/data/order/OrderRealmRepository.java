package br.com.libertsolutions.libertvendas.app.data.order;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmRepository;
import br.com.libertsolutions.libertvendas.app.domain.entity.OrderEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Order;

import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.orderMapper;

/**
 * @author Filipe Bezerra
 */
public class OrderRealmRepository extends RealmRepository<Order, OrderEntity>
        implements OrderRepository {

    public OrderRealmRepository() {
        super(OrderEntity.class, orderMapper());
    }
}
