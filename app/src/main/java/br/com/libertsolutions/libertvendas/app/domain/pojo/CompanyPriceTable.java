package br.com.libertsolutions.libertvendas.app.domain.pojo;

/**
 * @author Filipe Bezerra
 */
public class CompanyPriceTable {

    private final String id;

    private final Integer companyId;

    private final Integer priceTableId;

    private CompanyPriceTable(
            final String id, final Integer companyId, final Integer priceTableId) {
        this.id = id;
        this.companyId = companyId;
        this.priceTableId = priceTableId;
    }

    public static CompanyPriceTable from(final Company company, final PriceTable priceTable) {
        return new CompanyPriceTable(null, company.getCompanyId(), priceTable.getPriceTableId());
    }

    public static CompanyPriceTable of(
            final String id, final Integer companyId, final Integer priceTableId) {
        return new CompanyPriceTable(id, companyId, priceTableId);
    }

    public String getId() {
        return id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public Integer getPriceTableId() {
        return priceTableId;
    }
}
