package br.com.libertsolutions.libertvendas.app.data.company.customer;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmResultsSpecification;
import br.com.libertsolutions.libertvendas.app.domain.entity.CompanyCustomerEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.CustomerEntity;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.List;

import static br.com.libertsolutions.libertvendas.app.domain.entity.CompanyCustomerEntity.Fields.COMPANY_ID;
import static br.com.libertsolutions.libertvendas.app.domain.entity.CustomerEntity.Fields.ID;
import static br.com.libertsolutions.libertvendas.app.domain.entity.CustomerEntity.Fields.NAME;
import static io.realm.Sort.ASCENDING;

/**
 * @author Filipe Bezerra
 */
public class CustomersByCompanySpecification
        implements RealmResultsSpecification<CustomerEntity> {

    private final int companyId;

    public CustomersByCompanySpecification(final int companyId) {
        this.companyId = companyId;
    }

    @Override public RealmResults<CustomerEntity> toRealmResults(final Realm realm) {
        RealmResults<CompanyCustomerEntity> companyCustomers = realm
                .where(CompanyCustomerEntity.class)
                .equalTo(COMPANY_ID, companyId)
                .findAll();

        List<Integer> customerIds = new ArrayList<>();
        for (CompanyCustomerEntity customer : companyCustomers) {
            customerIds.add(customer.getCustomerId());
        }

        return realm.where(CustomerEntity.class)
                .in(ID, customerIds.toArray(new Integer [] {customerIds.size()}))
                .findAllSorted(NAME, ASCENDING);
    }
}
