package br.com.libertsolutions.libertvendas.app.domain.entity;

import br.com.libertsolutions.libertvendas.app.domain.pojo.CustomerStatus;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

import static br.com.libertsolutions.libertvendas.app.data.helper.RealmAutoIncrement.autoIncrementor;
import static br.com.libertsolutions.libertvendas.app.domain.pojo.CustomerStatus.STATUS_UNMODIFIED;

/**
 * @author Filipe Bezerra
 */
@RealmClass
public class CustomerEntity implements RealmModel {

    public static final class Fields {

        public static final String CUSTOMER_ID = "customerId";

        public static final String STATUS = "status";

        public static final String NAME = "name";
    }

    @PrimaryKey
    private Integer customerId;

    private String code;

    @Required
    private String name;

    private String fantasyName;

    @Required
    private Integer type;

    @Required
    private String cpfOrCnpj;

    private String contact;

    private String email;

    private String mainPhone;

    private String secondaryPhone;

    private String address;

    private String postalCode;

    private String district;

    private String addressNumber;

    private String addressComplement;

    private String defaultPriceTable;

    private CityEntity city;

    private Boolean active;

    private String lastChangeTime;

    private Integer status;

    public Integer getCustomerId() {
        return customerId;
    }

    public CustomerEntity withCustomerId(final Integer customerId) {
        if (customerId == null || customerId == 0) {
            this.customerId = autoIncrementor(getClass(), Fields.CUSTOMER_ID).getNextIdFromModel();
        } else {
            this.customerId = customerId;
        }
        return this;
    }

    public String getCode() {
        return code;
    }

    public CustomerEntity withCode(final String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public CustomerEntity withName(final String name) {
        this.name = name;
        return this;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public CustomerEntity withFantasyName(final String fantasyName) {
        this.fantasyName = fantasyName;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public CustomerEntity withType(final Integer type) {
        this.type = type;
        return this;
    }

    public String getCpfOrCnpj() {
        return cpfOrCnpj;
    }

    public CustomerEntity withCpfOrCnpj(final String cpfOrCnpj) {
        this.cpfOrCnpj = cpfOrCnpj;
        return this;
    }

    public String getContact() {
        return contact;
    }

    public CustomerEntity withContact(final String contact) {
        this.contact = contact;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public CustomerEntity withEmail(final String email) {
        this.email = email;
        return this;
    }

    public String getMainPhone() {
        return mainPhone;
    }

    public CustomerEntity withMainPhone(final String mainPhone) {
        this.mainPhone = mainPhone;
        return this;
    }

    public String getSecondaryPhone() {
        return secondaryPhone;
    }

    public CustomerEntity withSecondaryPhone(final String secondaryPhone) {
        this.secondaryPhone = secondaryPhone;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public CustomerEntity withAddress(final String address) {
        this.address = address;
        return this;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public CustomerEntity withPostalCode(final String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public String getDistrict() {
        return district;
    }

    public CustomerEntity withDistrict(final String district) {
        this.district = district;
        return this;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public CustomerEntity withAddressNumber(final String addressNumber) {
        this.addressNumber = addressNumber;
        return this;
    }

    public String getAddressComplement() {
        return addressComplement;
    }

    public CustomerEntity withAddressComplement(final String addressComplement) {
        this.addressComplement = addressComplement;
        return this;
    }

    public String getDefaultPriceTable() {
        return defaultPriceTable;
    }

    public CustomerEntity withDefaultPriceTable(final String defaultPriceTable) {
        this.defaultPriceTable = defaultPriceTable;
        return this;
    }

    public CityEntity getCity() {
        return city;
    }

    public CustomerEntity withCity(final CityEntity city) {
        this.city = city;
        return this;
    }

    public Boolean isActive() {
        return active;
    }

    public CustomerEntity withActive(final Boolean active) {
        this.active = active;
        return this;
    }

    public String getLastChangeTime() {
        return lastChangeTime;
    }

    public CustomerEntity withLastChangeTime(final String lastChangeTime) {
        this.lastChangeTime = lastChangeTime;
        return this;
    }

    @CustomerStatus public Integer getStatus() {
        return status;
    }

    public CustomerEntity withStatus(@CustomerStatus final Integer status) {
        if (status == null) {
            this.status = STATUS_UNMODIFIED;
        } else {
            this.status = status;
        }
        return this;
    }
}
