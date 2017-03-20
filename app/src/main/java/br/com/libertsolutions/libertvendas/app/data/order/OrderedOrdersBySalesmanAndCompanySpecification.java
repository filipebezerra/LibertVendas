package br.com.libertsolutions.libertvendas.app.data.order;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmResultsSpecification;
import br.com.libertsolutions.libertvendas.app.domain.entity.OrderEntity;
import io.realm.Realm;
import io.realm.RealmResults;

import static br.com.libertsolutions.libertvendas.app.domain.entity.OrderEntity.Fields.COMPANY_ID;
import static br.com.libertsolutions.libertvendas.app.domain.entity.OrderEntity.Fields.CUSTOMER_NAME;
import static br.com.libertsolutions.libertvendas.app.domain.entity.OrderEntity.Fields.SALESMAN_ID;
import static br.com.libertsolutions.libertvendas.app.domain.entity.OrderEntity.Fields.STATUS;
import static br.com.libertsolutions.libertvendas.app.domain.pojo.OrderStatus.STATUS_CANCELLED;
import static io.realm.Sort.ASCENDING;

/**
 * @author Filipe Bezerra
 */
public class OrderedOrdersBySalesmanAndCompanySpecification implements RealmResultsSpecification<OrderEntity> {

    private final int salesmanId;

    private final int companyId;

    public OrderedOrdersBySalesmanAndCompanySpecification(
            final int salesmanId, final int companyId) {
        this.salesmanId = salesmanId;
        this.companyId = companyId;
    }

    @Override public RealmResults<OrderEntity> toRealmResults(final Realm realm) {
        return  realm.where(OrderEntity.class)
                .equalTo(SALESMAN_ID, salesmanId)
                .equalTo(COMPANY_ID, companyId)
                .not()
                .equalTo(STATUS, STATUS_CANCELLED)
                .findAllSorted(CUSTOMER_NAME, ASCENDING);
    }
}
