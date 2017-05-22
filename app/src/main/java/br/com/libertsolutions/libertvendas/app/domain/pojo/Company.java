package br.com.libertsolutions.libertvendas.app.domain.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static br.com.libertsolutions.libertvendas.app.presentation.util.NumberUtils.withDefaultValue;

/**
 * @author Filipe Bezerra
 */
public class Company {

    @Expose
    @SerializedName("idEmpresa")
    private Integer companyId;

    @Expose
    @SerializedName("nome")
    private String name;

    @Expose
    @SerializedName("cnpj")
    private String cnpj;

    @Expose
    @SerializedName("idTabela")
    private Integer priceTableId;

    public Integer getCompanyId() {
        return companyId;
    }

    public Company withCompanyId(final Integer companyId) {
        this.companyId = companyId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Company withName(final String name) {
        this.name = name;
        return this;
    }

    public String getCnpj() {
        return cnpj;
    }

    public Company withCnpj(final String cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public Integer getPriceTableId() {
        return withDefaultValue(priceTableId, 0);
    }

    public Company withPriceTableId(final Integer priceTableId) {
        this.priceTableId = priceTableId;
        return this;
    }

    @Override public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Company company = (Company) o;

        return getCompanyId().equals(company.getCompanyId());
    }

    @Override public int hashCode() {
        return getCompanyId().hashCode();
    }

    @Override public String toString() {
        return getName();
    }
}
