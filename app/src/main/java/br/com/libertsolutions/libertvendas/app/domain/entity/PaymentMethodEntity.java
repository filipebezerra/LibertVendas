package br.com.libertsolutions.libertvendas.app.domain.entity;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * @author Filipe Bezerra
 */
@RealmClass
public class PaymentMethodEntity implements RealmModel {

    public static final class Fields {

        public static final String PAYMENT_METHOD_ID = "paymentMethodId";

        public static final String DESCRIPTION = "description";
    }

    @PrimaryKey
    private Integer paymentMethodId;

    private String code;

    @Required
    private String description;

    private Float discountPercentage;

    private Integer companyId;

    private Boolean active;

    private String lastChangeTime;

    public Integer getPaymentMethodId() {
        return paymentMethodId;
    }

    public PaymentMethodEntity withPaymentMethodId(final Integer paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
        return this;
    }

    public String getCode() {
        return code;
    }

    public PaymentMethodEntity withCode(final String code) {
        this.code = code;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public PaymentMethodEntity withDescription(final String description) {
        this.description = description;
        return this;
    }

    public Float getDiscountPercentage() {
        return discountPercentage;
    }

    public PaymentMethodEntity withDiscountPercentage(final Float discountPercentage) {
        this.discountPercentage = discountPercentage;
        return this;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public PaymentMethodEntity withCompanyId(final Integer companyId) {
        this.companyId = companyId;
        return this;
    }

    public Boolean isActive() {
        return active;
    }

    public PaymentMethodEntity withActive(final Boolean active) {
        this.active = active;
        return this;
    }

    public String getLastChangeTime() {
        return lastChangeTime;
    }

    public PaymentMethodEntity withLastChangeTime(final String lastChangeTime) {
        this.lastChangeTime = lastChangeTime;
        return this;
    }
}
