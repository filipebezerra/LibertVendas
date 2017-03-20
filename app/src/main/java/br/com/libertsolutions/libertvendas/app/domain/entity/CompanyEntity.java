package br.com.libertsolutions.libertvendas.app.domain.entity;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * @author Filipe Bezerra
 */
@RealmClass
public class CompanyEntity implements RealmModel {

    @PrimaryKey
    private Integer companyId;

    @Required
    private String name;

    @Required
    private String cnpj;

    private Integer priceTableId;

    public Integer getCompanyId() {
        return companyId;
    }

    public CompanyEntity withCompanyId(final Integer companyId) {
        this.companyId = companyId;
        return this;
    }

    public String getName() {
        return name;
    }

    public CompanyEntity withName(final String name) {
        this.name = name;
        return this;
    }

    public String getCnpj() {
        return cnpj;
    }

    public CompanyEntity withCnpj(final String cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public Integer getPriceTableId() {
        return priceTableId;
    }

    public CompanyEntity withPriceTableId(final Integer priceTableId) {
        this.priceTableId = priceTableId;
        return this;
    }
}
