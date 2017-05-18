package br.com.libertsolutions.libertvendas.app.data.company.paymentmethod;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmSingleSpecification;
import br.com.libertsolutions.libertvendas.app.domain.entity.CompanyPaymentMethodEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.PaymentMethodEntity;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.List;

import static br.com.libertsolutions.libertvendas.app.domain.entity.CompanyPaymentMethodEntity.Fields.COMPANY_ID;
import static br.com.libertsolutions.libertvendas.app.domain.entity.PaymentMethodEntity.Fields.PAYMENT_METHOD_ID;

/**
 * @author Filipe Bezerra
 */
public class PaymentMethodByIdSpecification
        implements RealmSingleSpecification<PaymentMethodEntity> {

    private final int paymentMethodId;

    private final int companyId;

    public PaymentMethodByIdSpecification(final int paymentMethodId, final int companyId) {
        this.paymentMethodId = paymentMethodId;
        this.companyId = companyId;
    }

    @Override public PaymentMethodEntity toSingle(final Realm realm) {
        RealmResults<CompanyPaymentMethodEntity> companyPaymentMethods = realm
                .where(CompanyPaymentMethodEntity.class)
                .equalTo(COMPANY_ID, companyId)
                .findAll();

        if (companyPaymentMethods.isEmpty()) {
            return null;
        }

        List<Integer> paymentMethodIds = new ArrayList<>();
        for (CompanyPaymentMethodEntity paymentMethod : companyPaymentMethods) {
            paymentMethodIds.add(paymentMethod.getPaymentMethodId());
        }

        return realm.where(PaymentMethodEntity.class)
                .in(PAYMENT_METHOD_ID,
                        paymentMethodIds.toArray(new Integer [] {paymentMethodIds.size()}))
                .equalTo(PAYMENT_METHOD_ID, paymentMethodId)
                .findFirst();
    }
}
