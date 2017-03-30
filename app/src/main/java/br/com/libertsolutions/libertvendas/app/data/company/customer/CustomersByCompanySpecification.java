package br.com.libertsolutions.libertvendas.app.data.company.customer;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmResultsSpecification;
import br.com.libertsolutions.libertvendas.app.domain.entity.CompanyCustomerEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.CustomerEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.CustomerStatus;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.List;

import static br.com.libertsolutions.libertvendas.app.domain.entity.CompanyCustomerEntity.Fields.COMPANY_ID;
import static br.com.libertsolutions.libertvendas.app.domain.entity.CustomerEntity.Fields.ID;
import static br.com.libertsolutions.libertvendas.app.domain.entity.CustomerEntity.Fields.NAME;
import static br.com.libertsolutions.libertvendas.app.domain.entity.CustomerEntity.Fields.STATUS;
import static io.realm.Sort.ASCENDING;

/**
 * @author Filipe Bezerra
 */
public class CustomersByCompanySpecification
        implements RealmResultsSpecification<CustomerEntity> {

    private final int mCompanyId;

    private int mByStatus = -1;

    public CustomersByCompanySpecification(final int companyId) {
        this.mCompanyId = companyId;
    }

    public CustomersByCompanySpecification byStatus(@CustomerStatus int status) {
        mByStatus = status;
        return this;
    }

    @Override public RealmResults<CustomerEntity> toRealmResults(final Realm realm) {
        RealmResults<CompanyCustomerEntity> companyCustomers = realm
                .where(CompanyCustomerEntity.class)
                .equalTo(COMPANY_ID, mCompanyId)
                .findAll();

        List<Integer> customerIds = new ArrayList<>();
        for (CompanyCustomerEntity customer : companyCustomers) {
            customerIds.add(customer.getCustomerId());
        }

        RealmQuery<CustomerEntity> query = realm
                .where(CustomerEntity.class)
                .in(ID, customerIds.toArray(new Integer[] { customerIds.size() }));

        if (mByStatus != -1) {
            query.equalTo(STATUS, mByStatus);
        }

        return query.findAllSorted(NAME, ASCENDING);
    }
}
