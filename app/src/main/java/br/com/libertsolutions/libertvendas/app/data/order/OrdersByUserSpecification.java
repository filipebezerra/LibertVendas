package br.com.libertsolutions.libertvendas.app.data.order;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmResultsSpecification;
import br.com.libertsolutions.libertvendas.app.domain.entity.OrderEntity;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import static br.com.libertsolutions.libertvendas.app.domain.entity.OrderEntity.Fields.COMPANY_ID;
import static br.com.libertsolutions.libertvendas.app.domain.entity.OrderEntity.Fields.CUSTOMER_NAME;
import static br.com.libertsolutions.libertvendas.app.domain.entity.OrderEntity.Fields.ISSUE_DATE;
import static br.com.libertsolutions.libertvendas.app.domain.entity.OrderEntity.Fields.SALESMAN_ID;
import static br.com.libertsolutions.libertvendas.app.domain.entity.OrderEntity.Fields.STATUS;
import static br.com.libertsolutions.libertvendas.app.domain.pojo.OrderStatus.STATUS_CANCELLED;
import static br.com.libertsolutions.libertvendas.app.domain.pojo.OrderStatus.STATUS_CREATED;
import static br.com.libertsolutions.libertvendas.app.domain.pojo.OrderStatus.STATUS_INVOICED;
import static br.com.libertsolutions.libertvendas.app.domain.pojo.OrderStatus.STATUS_MODIFIED;
import static br.com.libertsolutions.libertvendas.app.domain.pojo.OrderStatus.STATUS_SYNCED;
import static io.realm.Sort.ASCENDING;
import static io.realm.Sort.DESCENDING;

/**
 * @author Filipe Bezerra
 */
public class OrdersByUserSpecification implements RealmResultsSpecification<OrderEntity> {

    private final int mSalesmanId;

    private final int mCompanyId;

    private long mInitialIssueDate = 0;

    private long mFinalIssueDate = 0;

    private boolean mOrderedByIssueDate = false;

    private boolean mOrderedByCustomerName = false;

    private int status = OrderStatusSpecificationFilter.NONE;

    public OrdersByUserSpecification(final int salesmanId, final int companyId) {
        mSalesmanId = salesmanId;
        mCompanyId = companyId;
    }

    public OrdersByUserSpecification byIssueDate(
            final long initialIssueDate, final long finalIssueDate) {
        mInitialIssueDate = initialIssueDate;
        mFinalIssueDate = finalIssueDate;
        return this;
    }

    public OrdersByUserSpecification byStatus(@OrderStatusSpecificationFilter int status) {
        this.status = status;
        return this;
    }

    public OrdersByUserSpecification orderByIssueDate() {
        mOrderedByIssueDate = true;
        mOrderedByCustomerName = false;
        return this;
    }

    public OrdersByUserSpecification orderByCustomerName() {
        mOrderedByCustomerName = true;
        mOrderedByIssueDate = false;
        return this;
    }

    @Override public RealmResults<OrderEntity> toRealmResults(final Realm realm) {
        RealmQuery<OrderEntity> query = createQuery(realm);

        switch (status) {
            case OrderStatusSpecificationFilter.NOT_CANCELLED: {
                query.not().equalTo(STATUS, STATUS_CANCELLED);
                break;
            }
            case OrderStatusSpecificationFilter.CREATED_OR_MODIFIED: {
                query.beginGroup()
                        .equalTo(STATUS, STATUS_CREATED)
                        .or()
                        .equalTo(STATUS, STATUS_MODIFIED)
                        .endGroup();
                break;
            }
            case OrderStatusSpecificationFilter.SYNCED: {
                query.equalTo(STATUS, STATUS_SYNCED);
                break;
            }
            case OrderStatusSpecificationFilter.INVOICED: {
                query.equalTo(STATUS, STATUS_INVOICED);
                break;
            }
        }

        ifByIssueDate(query);

        return findAll(query);
    }

    private RealmQuery<OrderEntity> createQuery(final Realm realm) {
        return realm
                .where(OrderEntity.class)
                .equalTo(SALESMAN_ID, mSalesmanId)
                .equalTo(COMPANY_ID, mCompanyId);
    }

    private void ifByIssueDate(RealmQuery<OrderEntity> query) {
        if (mInitialIssueDate != 0 && mFinalIssueDate != 0) {
            query
                    .greaterThanOrEqualTo(ISSUE_DATE, mInitialIssueDate)
                    .lessThanOrEqualTo(ISSUE_DATE, mFinalIssueDate);
        }
    }

    private RealmResults<OrderEntity> findAll(RealmQuery<OrderEntity> query) {
        if (mOrderedByIssueDate) {
            return query.findAllSorted(ISSUE_DATE, DESCENDING);
        } else if (mOrderedByCustomerName) {
            return query.findAllSorted(CUSTOMER_NAME, ASCENDING);
        } else {
            return query.findAll();
        }
    }
}
