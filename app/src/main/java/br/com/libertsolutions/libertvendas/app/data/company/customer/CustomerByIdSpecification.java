package br.com.libertsolutions.libertvendas.app.data.company.customer;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmSingleSpecification;
import br.com.libertsolutions.libertvendas.app.domain.entity.CustomerEntity;
import io.realm.Realm;

import static br.com.libertsolutions.libertvendas.app.domain.entity.CustomerEntity.Fields.ID_CUSTOMER;

/**
 * @author Filipe Bezerra
 */
public class CustomerByIdSpecification
        implements RealmSingleSpecification<CustomerEntity> {

    private final int customerId;

    public CustomerByIdSpecification(final int customerId) {
        this.customerId = customerId;
    }

    @Override public CustomerEntity toSingle(final Realm realm) {
        return realm.where(CustomerEntity.class)
                .equalTo(ID_CUSTOMER, customerId)
                .findFirst();
    }
}
