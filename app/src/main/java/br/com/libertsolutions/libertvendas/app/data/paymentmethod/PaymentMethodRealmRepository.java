package br.com.libertsolutions.libertvendas.app.data.paymentmethod;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmRepository;
import br.com.libertsolutions.libertvendas.app.domain.entity.PaymentMethodEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.PaymentMethod;

import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.paymentMethodMapper;

/**
 * @author Filipe Bezerra
 */
public class PaymentMethodRealmRepository extends RealmRepository<PaymentMethod, PaymentMethodEntity>
        implements PaymentMethodRepository {

    public PaymentMethodRealmRepository() {
        super(PaymentMethodEntity.class, paymentMethodMapper());
    }
}
