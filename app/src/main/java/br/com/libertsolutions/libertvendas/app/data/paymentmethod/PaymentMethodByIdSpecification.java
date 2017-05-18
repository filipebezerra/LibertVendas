package br.com.libertsolutions.libertvendas.app.data.paymentmethod;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmSingleSpecification;
import br.com.libertsolutions.libertvendas.app.domain.entity.PaymentMethodEntity;
import io.realm.Realm;

import static br.com.libertsolutions.libertvendas.app.domain.entity.PaymentMethodEntity.Fields.PAYMENT_METHOD_ID;

/**
 * @author Filipe Bezerra
 */
public class PaymentMethodByIdSpecification
        implements RealmSingleSpecification<PaymentMethodEntity> {

    private final int paymentMethodId;

    public PaymentMethodByIdSpecification(final int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    @Override public PaymentMethodEntity toSingle(final Realm realm) {
        return realm.where(PaymentMethodEntity.class)
                .equalTo(PAYMENT_METHOD_ID, paymentMethodId)
                .findFirst();
    }
}
