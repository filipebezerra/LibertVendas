package br.com.libertsolutions.libertvendas.app.data.customer;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmSingleSpecification;
import br.com.libertsolutions.libertvendas.app.domain.entity.CustomerEntity;
import io.realm.Realm;

import static br.com.libertsolutions.libertvendas.app.domain.entity.CustomerEntity.Fields.CUSTOMER_ID;

/**
 * @author Filipe Bezerra
 */
public class CustomerByCustomerIdSpecification
        implements RealmSingleSpecification<CustomerEntity> {

    private final int customerId;

    public CustomerByCustomerIdSpecification(final int customerId) {
        this.customerId = customerId;
    }

    @Override public CustomerEntity toSingle(final Realm realm) {
        return realm.where(CustomerEntity.class)
                .equalTo(CUSTOMER_ID, customerId)
                .findFirst();
    }
}
