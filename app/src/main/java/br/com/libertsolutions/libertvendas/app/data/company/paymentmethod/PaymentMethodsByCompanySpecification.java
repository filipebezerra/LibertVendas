package br.com.libertsolutions.libertvendas.app.data.company.paymentmethod;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmResultsSpecification;
import br.com.libertsolutions.libertvendas.app.domain.entity.CompanyPaymentMethodEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.PaymentMethodEntity;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.List;

import static br.com.libertsolutions.libertvendas.app.domain.entity.CompanyPaymentMethodEntity.Fields.COMPANY_ID;
import static br.com.libertsolutions.libertvendas.app.domain.entity.PaymentMethodEntity.Fields.DESCRIPTION;
import static br.com.libertsolutions.libertvendas.app.domain.entity.PaymentMethodEntity.Fields.PAYMENT_METHOD_ID;
import static io.realm.Sort.ASCENDING;

/**
 * @author Filipe Bezerra
 */
public class PaymentMethodsByCompanySpecification
        implements RealmResultsSpecification<PaymentMethodEntity> {

    private final int companyId;

    public PaymentMethodsByCompanySpecification(final int companyId) {
        this.companyId = companyId;
    }

    @Override public RealmResults<PaymentMethodEntity> toRealmResults(final Realm realm) {
        RealmResults<CompanyPaymentMethodEntity> companyPaymentMethods = realm
                .where(CompanyPaymentMethodEntity.class)
                .equalTo(COMPANY_ID, companyId)
                .findAll();

        List<Integer> paymentMethodIds = new ArrayList<>();
        for (CompanyPaymentMethodEntity paymentMethod : companyPaymentMethods) {
            paymentMethodIds.add(paymentMethod.getPaymentMethodId());
        }

        return realm.where(PaymentMethodEntity.class)
                .in(PAYMENT_METHOD_ID,
                        paymentMethodIds.toArray(new Integer [] {paymentMethodIds.size()}))
                .findAllSorted(DESCRIPTION, ASCENDING);
    }
}
