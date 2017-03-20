package br.com.libertsolutions.libertvendas.app.data.paymentmethod;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmMapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.PaymentMethodEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.PaymentMethod;

/**
 * @author Filipe Bezerra
 */
public class PaymentMethodRealmMapper extends RealmMapper<PaymentMethod, PaymentMethodEntity> {

    @Override public PaymentMethodEntity toEntity(final PaymentMethod object) {
        return new PaymentMethodEntity()
                .withPaymentMethodId(object.getPaymentMethodId())
                .withCode(object.getCode())
                .withDescription(object.getDescription())
                .withDiscountPercentage(object.getDiscountPercentage())
                .withCompanyId(object.getCompanyId())
                .withActive(object.isActive())
                .withLastChangeTime(object.getLastChangeTime());
    }

    @Override public PaymentMethod toViewObject(final PaymentMethodEntity entity) {
        return new PaymentMethod()
                .withPaymentMethodId(entity.getPaymentMethodId())
                .withCode(entity.getCode())
                .withDescription(entity.getDescription())
                .withDiscountPercentage(entity.getDiscountPercentage())
                .withCompanyId(entity.getCompanyId())
                .withActive(entity.isActive())
                .withLastChangeTime(entity.getLastChangeTime());
    }
}
