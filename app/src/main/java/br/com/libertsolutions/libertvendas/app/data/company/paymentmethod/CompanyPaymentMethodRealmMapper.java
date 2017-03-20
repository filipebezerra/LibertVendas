package br.com.libertsolutions.libertvendas.app.data.company.paymentmethod;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmMapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.CompanyPaymentMethodEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.CompanyPaymentMethod;

/**
 * @author Filipe Bezerra
 */
public class CompanyPaymentMethodRealmMapper
        extends RealmMapper<CompanyPaymentMethod, CompanyPaymentMethodEntity> {

    @Override public CompanyPaymentMethodEntity toEntity(final CompanyPaymentMethod object) {
        return new CompanyPaymentMethodEntity()
                .withId(object.getCompanyId(), object.getPaymentMethodId());
    }

    @Override public CompanyPaymentMethod toViewObject(final CompanyPaymentMethodEntity entity) {
        return CompanyPaymentMethod
                .of(entity.getId(), entity.getCompanyId(), entity.getPaymentMethodId());
    }
}
