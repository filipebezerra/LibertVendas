package br.com.libertsolutions.libertvendas.app.data.company.customer;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmMapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.CompanyCustomerEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.CompanyCustomer;

/**
 * @author Filipe Bezerra
 */
public class CompanyCustomerRealmMapper extends RealmMapper<CompanyCustomer, CompanyCustomerEntity> {

    @Override public CompanyCustomerEntity toEntity(final CompanyCustomer object) {
        return new CompanyCustomerEntity()
                .withId(object.getCompanyId(), object.getCustomerId());
    }

    @Override public CompanyCustomer toViewObject(final CompanyCustomerEntity entity) {
        return CompanyCustomer
                .of(entity.getId(), entity.getCompanyId(), entity.getCustomerId());
    }
}
