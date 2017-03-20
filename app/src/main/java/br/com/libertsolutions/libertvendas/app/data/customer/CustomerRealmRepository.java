package br.com.libertsolutions.libertvendas.app.data.customer;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmRepository;
import br.com.libertsolutions.libertvendas.app.domain.entity.CustomerEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Customer;

import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.customerMapper;

/**
 * @author Filipe Bezerra
 */
public class CustomerRealmRepository extends RealmRepository<Customer, CustomerEntity>
        implements CustomerRepository {

    public CustomerRealmRepository() {
        super(CustomerEntity.class, customerMapper());
    }
}
