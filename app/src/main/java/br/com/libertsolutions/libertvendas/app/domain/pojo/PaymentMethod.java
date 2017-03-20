package br.com.libertsolutions.libertvendas.app.domain.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Filipe Bezerra
 */
public class PaymentMethod {

    @Expose
    @SerializedName("idFormPgto")
    private Integer paymentMethodId;

    @Expose
    @SerializedName("codigo")
    private String code;

    @Expose
    @SerializedName("descricao")
    private String description;

    @Expose
    @SerializedName("perDesc")
    private Float discountPercentage;

    @Expose
    @SerializedName("id_empresa")
    private Integer companyId;

    @Expose
    @SerializedName("ativo")
    private Boolean active;

    @Expose
    @SerializedName("ultimaAlteracao")
    private String lastChangeTime;

    public Integer getPaymentMethodId() {
        return paymentMethodId;
    }

    public PaymentMethod withPaymentMethodId(final Integer paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
        return this;
    }

    public String getCode() {
        return code;
    }

    public PaymentMethod withCode(final String code) {
        this.code = code;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public PaymentMethod withDescription(final String description) {
        this.description = description;
        return this;
    }

    public Float getDiscountPercentage() {
        return discountPercentage;
    }

    public PaymentMethod withDiscountPercentage(final Float discountPercentage) {
        this.discountPercentage = discountPercentage;
        return this;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public PaymentMethod withCompanyId(final Integer companyId) {
        this.companyId = companyId;
        return this;
    }

    public Boolean isActive() {
        return active;
    }

    public PaymentMethod withActive(final Boolean active) {
        this.active = active;
        return this;
    }

    public String getLastChangeTime() {
        return lastChangeTime;
    }

    public PaymentMethod withLastChangeTime(final String lastChangeTime) {
        this.lastChangeTime = lastChangeTime;
        return this;
    }

    @Override public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PaymentMethod that = (PaymentMethod) o;

        return getPaymentMethodId().equals(that.getPaymentMethodId());
    }

    @Override public int hashCode() {
        return getPaymentMethodId().hashCode();
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("PaymentMethod{");
        sb.append("paymentMethodId=").append(paymentMethodId);
        sb.append(", code='").append(code).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", discountPercentage=").append(discountPercentage);
        sb.append(", companyId=").append(companyId);
        sb.append(", active=").append(active);
        sb.append(", lastChangeTime='").append(lastChangeTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
