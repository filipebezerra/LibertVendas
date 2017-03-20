package br.com.libertsolutions.libertvendas.app.data.company.customer;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmRepository;
import br.com.libertsolutions.libertvendas.app.domain.entity.CompanyCustomerEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.CompanyCustomer;

import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.companyCustomerMapper;

/**
 * @author Filipe Bezerra
 */
public class CompanyCustomerRealmRepository
        extends RealmRepository<CompanyCustomer, CompanyCustomerEntity>
        implements CompanyCustomerRepository {

    public CompanyCustomerRealmRepository() {
        super(CompanyCustomerEntity.class, companyCustomerMapper());
    }
}
