package br.com.libertsolutions.libertvendas.app.domain.entity;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * @author Filipe Bezerra
 */
@RealmClass
public class CompanyCustomerEntity implements RealmModel {

    public static final class Fields {

        public static final String COMPANY_ID = "companyId";
    }

    @PrimaryKey
    private String id;

    @Required
    private Integer companyId;

    @Required
    private Integer customerId;

    public CompanyCustomerEntity withId(Integer companyId, Integer customerId) {
        this.id = String.valueOf(companyId) + String.valueOf(customerId);
        this.companyId = companyId;
        this.customerId = customerId;
        return this;
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
