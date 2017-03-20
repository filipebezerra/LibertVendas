package br.com.libertsolutions.libertvendas.app.domain.pojo;

/**
 * @author Filipe Bezerra
 */
public class CompanyCustomer {

    private final String id;

    private final Integer companyId;

    private final Integer customerId;

    private CompanyCustomer(final String id, final Integer companyId, final Integer customerId) {
        this.id = id;
        this.companyId = companyId;
        this.customerId = customerId;
    }

    public static CompanyCustomer from(final Company company, final Customer customer) {
        return new CompanyCustomer(null, company.getCompanyId(), customer.getCustomerId());
    }

    public static CompanyCustomer of(
            final String id, final Integer companyId, final Integer customerId) {
        return new CompanyCustomer(id, companyId, customerId);
    }

    public String getId() {
        return id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public Integer getCustomerId() {
        return customerId;
    }
}
