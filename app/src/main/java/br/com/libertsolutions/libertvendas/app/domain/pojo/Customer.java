package br.com.libertsolutions.libertvendas.app.domain.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Filipe Bezerra
 */
public class Customer {

    @Expose
    @SerializedName("appKey")
    private Integer id;

    @Expose
    @SerializedName("idCliente")
    private Integer customerId;

    @Expose
    @SerializedName("codigo")
    private String code;

    @Expose
    @SerializedName("nome")
    private String name;

    @Expose
    @SerializedName("nomeFantasia")
    private String fantasyName;

    @Expose
    @SerializedName("tipo")
    private Integer type;

    @Expose
    @SerializedName("cpfCnpj")
    private String cpfOrCnpj;

    @Expose
    @SerializedName("contato")
    private String contact;

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("telefone")
    private String mainPhone;

    @Expose
    @SerializedName("telefone2")
    private String secondaryPhone;

    @Expose
    @SerializedName("endereco")
    private String address;

    @Expose
    @SerializedName("cep")
    private String postalCode;

    @Expose
    @SerializedName("bairro")
    private String district;

    @Expose
    @SerializedName("numero")
    private String addressNumber;

    @Expose
    @SerializedName("complemento")
    private String addressComplement;

    @Expose
    @SerializedName("tabelaPadrao")
    private String defaultPriceTable;

    @Expose
    @SerializedName("Cidade")
    private City city;

    @Expose
    @SerializedName("ativo")
    private Boolean active;

    @Expose
    @SerializedName("ultimaAlteracao")
    private String lastChangeTime;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public Customer withId(final Integer id) {
        this.id = id;
        return this;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Customer withCustomerId(final Integer customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Customer withCode(final String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public Customer withName(final String name) {
        this.name = name;
        return this;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public Customer withFantasyName(final String fantasyName) {
        this.fantasyName = fantasyName;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public Customer withType(final Integer type) {
        this.type = type;
        return this;
    }

    public String getCpfOrCnpj() {
        return cpfOrCnpj;
    }

    public Customer withCpfOrCnpj(final String cpfOrCnpj) {
        this.cpfOrCnpj = cpfOrCnpj;
        return this;
    }

    public String getContact() {
        return contact;
    }

    public Customer withContact(final String contact) {
        this.contact = contact;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Customer withEmail(final String email) {
        this.email = email;
        return this;
    }

    public String getMainPhone() {
        return mainPhone;
    }

    public Customer withMainPhone(final String mainPhone) {
        this.mainPhone = mainPhone;
        return this;
    }

    public String getSecondaryPhone() {
        return secondaryPhone;
    }

    public Customer withSecondaryPhone(final String secondaryPhone) {
        this.secondaryPhone = secondaryPhone;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Customer withAddress(final String address) {
        this.address = address;
        return this;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Customer withPostalCode(final String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public String getDistrict() {
        return district;
    }

    public Customer withDistrict(final String district) {
        this.district = district;
        return this;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public Customer withAddressNumber(final String addressNumber) {
        this.addressNumber = addressNumber;
        return this;
    }

    public String getAddressComplement() {
        return addressComplement;
    }

    public Customer withAddressComplement(final String addressComplement) {
        this.addressComplement = addressComplement;
        return this;
    }

    public String getDefaultPriceTable() {
        return defaultPriceTable;
    }

    public Customer withDefaultPriceTable(final String defaultPriceTable) {
        this.defaultPriceTable = defaultPriceTable;
        return this;
    }

    public City getCity() {
        return city;
    }

    public Customer withCity(final City city) {
        this.city = city;
        return this;
    }

    public Boolean isActive() {
        return active;
    }

    public Customer withActive(final Boolean active) {
        this.active = active;
        return this;
    }

    public String getLastChangeTime() {
        return lastChangeTime;
    }

    public Customer withLastChangeTime(final String lastChangeTime) {
        this.lastChangeTime = lastChangeTime;
        return this;
    }

    @CustomerStatus public Integer getStatus() {
        return status;
    }

    public Customer withStatus(@CustomerStatus final Integer status) {
        this.status = status;
        return this;
    }

    @Override public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Customer customer = (Customer) o;

        return getCpfOrCnpj().equals(customer.getCpfOrCnpj());
    }

    @Override public int hashCode() {
        return getCpfOrCnpj().hashCode();
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("Customer{");
        sb.append("id=").append(id);
        sb.append(", customerId=").append(customerId);
        sb.append(", code='").append(code).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", fantasyName='").append(fantasyName).append('\'');
        sb.append(", type=").append(type);
        sb.append(", cpfOrCnpj='").append(cpfOrCnpj).append('\'');
        sb.append(", contact='").append(contact).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", mainPhone='").append(mainPhone).append('\'');
        sb.append(", secondaryPhone='").append(secondaryPhone).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", postalCode='").append(postalCode).append('\'');
        sb.append(", district='").append(district).append('\'');
        sb.append(", addressNumber='").append(addressNumber).append('\'');
        sb.append(", addressComplement='").append(addressComplement).append('\'');
        sb.append(", defaultPriceTable='").append(defaultPriceTable).append('\'');
        sb.append(", city=").append(city);
        sb.append(", active=").append(active);
        sb.append(", lastChangeTime='").append(lastChangeTime).append('\'');
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
