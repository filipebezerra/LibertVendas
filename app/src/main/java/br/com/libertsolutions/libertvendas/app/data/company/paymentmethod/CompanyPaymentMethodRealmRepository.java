package br.com.libertsolutions.libertvendas.app.data.company.paymentmethod;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmRepository;
import br.com.libertsolutions.libertvendas.app.domain.entity.CompanyPaymentMethodEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.CompanyPaymentMethod;

import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.companyPaymentMethodMapper;

/**
 * @author Filipe Bezerra
 */
public class CompanyPaymentMethodRealmRepository
        extends RealmRepository<CompanyPaymentMethod, CompanyPaymentMethodEntity>
        implements CompanyPaymentMethodRepository {

    public CompanyPaymentMethodRealmRepository() {
        super(CompanyPaymentMethodEntity.class, companyPaymentMethodMapper());
    }
}
