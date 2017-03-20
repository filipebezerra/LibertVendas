package br.com.libertsolutions.libertvendas.app.domain.pojo;

/**
 * @author Filipe Bezerra
 */
public class CompanyPaymentMethod {

    private final String id;

    private final Integer companyId;

    private final Integer paymentMethodId;

    private CompanyPaymentMethod(
            final String id, final Integer companyId, final Integer paymentMethodId) {
        this.id = id;
        this.companyId = companyId;
        this.paymentMethodId = paymentMethodId;
    }

    public static CompanyPaymentMethod from(
            final Company company, final PaymentMethod paymentMethod) {
        return new CompanyPaymentMethod(
                null, company.getCompanyId(), paymentMethod.getPaymentMethodId());
    }

    public static CompanyPaymentMethod of(
            final String id, final Integer companyId, final Integer paymentMethodId) {
        return new CompanyPaymentMethod(id, companyId, paymentMethodId);
    }

    public String getId() {
        return id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public Integer getPaymentMethodId() {
        return paymentMethodId;
    }
}
