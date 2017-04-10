package br.com.libertsolutions.libertvendas.app.data.company.customer;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmSingleSpecification;
import br.com.libertsolutions.libertvendas.app.domain.entity.CompanyCustomerEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.CustomerEntity;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.List;

import static br.com.libertsolutions.libertvendas.app.domain.entity.CompanyCustomerEntity.Fields.COMPANY_ID;
import static br.com.libertsolutions.libertvendas.app.domain.entity.CustomerEntity.Fields.ID;
import static br.com.libertsolutions.libertvendas.app.domain.entity.CustomerEntity.Fields.ID_CUSTOMER;

/**
 * @author Filipe Bezerra
 */
public class CustomerByCompanySpecification
        implements RealmSingleSpecification<CustomerEntity> {

    private final int companyId;

    private final int customerId;

    public CustomerByCompanySpecification(final int companyId, final int customerId) {
        this.companyId = companyId;
        this.customerId = customerId;
    }

    @Override public CustomerEntity toSingle(final Realm realm) {
        RealmResults<CompanyCustomerEntity> companyCustomers = realm
                .where(CompanyCustomerEntity.class)
                .equalTo(COMPANY_ID, companyId)
                .findAll();

        List<Integer> customerIds = new ArrayList<>();
        for (CompanyCustomerEntity customer : companyCustomers) {
            customerIds.add(customer.getCustomerId());
        }

        return realm
                .where(CustomerEntity.class)
                .in(ID, customerIds.toArray(new Integer[] { customerIds.size() }))
                .equalTo(ID_CUSTOMER, customerId)
                .findFirst();
    }
}
