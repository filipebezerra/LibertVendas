package br.com.libertsolutions.libertvendas.app.data.company.customer;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmSingleSpecification;
import br.com.libertsolutions.libertvendas.app.domain.entity.CompanyCustomerEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.CustomerEntity;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.List;

import static br.com.libertsolutions.libertvendas.app.domain.entity.CompanyCustomerEntity.Fields.COMPANY_ID;
import static br.com.libertsolutions.libertvendas.app.domain.entity.CustomerEntity.Fields.CUSTOMER_ID;
import static br.com.libertsolutions.libertvendas.app.domain.entity.CustomerEntity.Fields.ID;

/**
 * @author Filipe Bezerra
 */
public class CustomerByCustomerIdSpecification
        implements RealmSingleSpecification<CustomerEntity> {

    private final int customerId;

    private final int companyId;

    public CustomerByCustomerIdSpecification(final int customerId, final int companyId) {
        this.customerId = customerId;
        this.companyId = companyId;
    }

    @Override public CustomerEntity toSingle(final Realm realm) {
        RealmResults<CompanyCustomerEntity> companyCustomers = realm
                .where(CompanyCustomerEntity.class)
                .equalTo(COMPANY_ID, companyId)
                .findAll();

        if (companyCustomers.isEmpty()) {
            return null;
        }

        List<Integer> customerIds = new ArrayList<>();
        for (CompanyCustomerEntity customer : companyCustomers) {
            customerIds.add(customer.getCustomerId());
        }

        return realm.where(CustomerEntity.class)
                .in(ID, customerIds.toArray(new Integer[] { customerIds.size() }))
                .equalTo(CUSTOMER_ID, customerId)
                .findFirst();
    }
}
