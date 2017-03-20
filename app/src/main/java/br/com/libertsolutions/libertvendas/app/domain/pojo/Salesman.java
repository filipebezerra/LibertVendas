package br.com.libertsolutions.libertvendas.app.domain.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class Salesman {

    @Expose
    @SerializedName("idVendedor")
    private Integer salesmanId;

    @Expose
    @SerializedName("codigo")
    private String code;

    @Expose
    @SerializedName("nome")
    private String name;

    @Expose
    @SerializedName("cpfCnpj")
    private String cpfOrCnpj;

    @Expose
    @SerializedName("telefone")
    private String phoneNumber;

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("ativo")
    private Boolean active;

    @Expose
    @SerializedName("ultimaAlteracao")
    private String lastChangeTime;

    @Expose
    @SerializedName("aplicaDesconto")
    private Boolean canApplyDiscount;

    @Expose
    @SerializedName("Empresas")
    private List<Company> companies = new ArrayList<>();

    public Integer getSalesmanId() {
        return salesmanId;
    }

    public Salesman withSalesmanId(final Integer salesmanId) {
        this.salesmanId = salesmanId;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Salesman withCode(final String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public Salesman withName(final String name) {
        this.name = name;
        return this;
    }

    public String getCpfOrCnpj() {
        return cpfOrCnpj;
    }

    public Salesman withCpfOrCnpj(final String cpfOrCnpj) {
        this.cpfOrCnpj = cpfOrCnpj;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Salesman withPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Salesman withEmail(final String email) {
        this.email = email;
        return this;
    }

    public Boolean getActive() {
        return active;
    }

    public Salesman withActive(final Boolean active) {
        this.active = active;
        return this;
    }

    public String getLastChangeTime() {
        return lastChangeTime;
    }

    public Salesman withLastChangeTime(final String lastChangeTime) {
        this.lastChangeTime = lastChangeTime;
        return this;
    }

    public Boolean getCanApplyDiscount() {
        return canApplyDiscount;
    }

    public Salesman withCanApplyDiscount(final Boolean canApplyDiscount) {
        this.canApplyDiscount = canApplyDiscount;
        return this;
    }

    public List<Company> getCompanies() {
        return companies == null ? new ArrayList<>() : companies;
    }

    public Salesman withCompanies(final List<Company> companies) {
        if (companies == null) {
            this.companies = new ArrayList<>();
        } else {
            this.companies = companies;
        }
        return this;
    }

    @Override public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Salesman salesman = (Salesman) o;

        return getSalesmanId().equals(salesman.getSalesmanId());
    }

    @Override public int hashCode() {
        return getSalesmanId().hashCode();
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("Salesman{");
        sb.append("salesmanId=").append(salesmanId);
        sb.append(", code='").append(code).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", cpfOrCnpj='").append(cpfOrCnpj).append('\'');
        sb.append(", phoneNumber='").append(phoneNumber).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", active=").append(active);
        sb.append(", lastChangeTime='").append(lastChangeTime).append('\'');
        sb.append(", canApplyDiscount=").append(canApplyDiscount);
        sb.append(", companies=").append(companies);
        sb.append('}');
        return sb.toString();
    }
}
