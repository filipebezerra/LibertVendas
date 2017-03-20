package br.com.libertsolutions.libertvendas.app.data.order;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmResultsSpecification;
import br.com.libertsolutions.libertvendas.app.domain.entity.OrderEntity;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import static br.com.libertsolutions.libertvendas.app.domain.entity.OrderEntity.Fields.COMPANY_ID;
import static br.com.libertsolutions.libertvendas.app.domain.entity.OrderEntity.Fields.ISSUE_DATE;
import static br.com.libertsolutions.libertvendas.app.domain.entity.OrderEntity.Fields.SALESMAN_ID;
import static br.com.libertsolutions.libertvendas.app.domain.entity.OrderEntity.Fields.STATUS;
import static br.com.libertsolutions.libertvendas.app.domain.pojo.OrderStatus.STATUS_CREATED;
import static br.com.libertsolutions.libertvendas.app.domain.pojo.OrderStatus.STATUS_MODIFIED;
import static io.realm.Sort.DESCENDING;

/**
 * @author Filipe Bezerra
 */
public class OrdersBySalesmanAndCompanySpecification implements RealmResultsSpecification<OrderEntity> {

    private final int salesmanId;

    private final int companyId;

    private final boolean onlyCreatedOrModified;

    public OrdersBySalesmanAndCompanySpecification(
            final int salesmanId, final int companyId, final boolean onlyCreatedOrModified) {
        this.onlyCreatedOrModified = onlyCreatedOrModified;
        this.salesmanId = salesmanId;
        this.companyId = companyId;
    }

    @Override public RealmResults<OrderEntity> toRealmResults(final Realm realm) {
        RealmQuery<OrderEntity> query = realm.where(OrderEntity.class)
                .equalTo(SALESMAN_ID, salesmanId)
                .equalTo(COMPANY_ID, companyId);

        if (onlyCreatedOrModified) {
            query.beginGroup()
                    .equalTo(STATUS, STATUS_CREATED)
                    .or()
                    .equalTo(STATUS, STATUS_MODIFIED)
                    .endGroup();
        }

        return query.findAllSorted(ISSUE_DATE, DESCENDING);
    }
}
