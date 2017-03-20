package br.com.libertsolutions.libertvendas.app.domain.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Filipe Bezerra
 */
public class Product {

    @Expose
    @SerializedName("idProduto")
    private Integer productId;

    @Expose
    @SerializedName("codigo")
    private String code;

    @Expose
    @SerializedName("codigoBarras")
    private String barCode;

    @Expose
    @SerializedName("descricao")
    private String description;

    @Expose
    @SerializedName("un")
    private String unit;

    @Expose
    @SerializedName("grupo")
    private String group;

    @Expose
    @SerializedName("quantidade")
    private Float stockQuantity;

    @Expose
    @SerializedName("obs")
    private String observation;

    @Expose
    @SerializedName("ativo")
    private Boolean active;

    @Expose
    @SerializedName("ultimaAlteracao")
    private String lastChangeTime;

    public Integer getProductId() {
        return productId;
    }

    public Product withProductId(final Integer productId) {
        this.productId = productId;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Product withCode(final String code) {
        this.code = code;
        return this;
    }

    public String getBarCode() {
        return barCode;
    }

    public Product withBarCode(final String barCode) {
        this.barCode = barCode;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Product withDescription(final String description) {
        this.description = description;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public Product withUnit(final String unit) {
        this.unit = unit;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public Product withGroup(final String group) {
        this.group = group;
        return this;
    }

    public Float getStockQuantity() {
        return stockQuantity;
    }

    public Product withStockQuantity(final Float stockQuantity) {
        this.stockQuantity = stockQuantity;
        return this;
    }

    public String getObservation() {
        return observation;
    }

    public Product withObservation(final String observation) {
        this.observation = observation;
        return this;
    }

    public Boolean isActive() {
        return active;
    }

    public Product withActive(final Boolean active) {
        this.active = active;
        return this;
    }

    public String getLastChangeTime() {
        return lastChangeTime;
    }

    public Product withLastChangeTime(final String lastChangeTime) {
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

        Product product = (Product) o;

        return getProductId().equals(product.getProductId());
    }

    @Override public int hashCode() {
        return getProductId().hashCode();
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("Product{");
        sb.append("productId=").append(productId);
        sb.append(", code='").append(code).append('\'');
        sb.append(", barCode='").append(barCode).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", unit='").append(unit).append('\'');
        sb.append(", group='").append(group).append('\'');
        sb.append(", stockQuantity=").append(stockQuantity);
        sb.append(", observation='").append(observation).append('\'');
        sb.append(", active=").append(active);
        sb.append(", lastChangeTime='").append(lastChangeTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
