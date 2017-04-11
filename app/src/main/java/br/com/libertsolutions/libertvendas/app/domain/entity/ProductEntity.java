package br.com.libertsolutions.libertvendas.app.domain.entity;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * @author Filipe Bezerra
 */
@RealmClass
public class ProductEntity implements RealmModel {

    public static final class Fields {

        public static final String ID_PRODUCT = "productId";
    }

    @PrimaryKey
    private Integer productId;

    private String code;

    private String barCode;

    @Required
    private String description;

    private String unit;

    private String group;

    private Float stockQuantity;

    private String observation;

    private Boolean active;

    private String lastChangeTime;

    public Integer getProductId() {
        return productId;
    }

    public ProductEntity withProductId(final Integer productId) {
        this.productId = productId;
        return this;
    }

    public String getCode() {
        return code;
    }

    public ProductEntity withCode(final String code) {
        this.code = code;
        return this;
    }

    public String getBarCode() {
        return barCode;
    }

    public ProductEntity withBarCode(final String barCode) {
        this.barCode = barCode;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductEntity withDescription(final String description) {
        this.description = description;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public ProductEntity withUnit(final String unit) {
        this.unit = unit;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public ProductEntity withGroup(final String group) {
        this.group = group;
        return this;
    }

    public Float getStockQuantity() {
        return stockQuantity;
    }

    public ProductEntity withStockQuantity(final Float stockQuantity) {
        this.stockQuantity = stockQuantity;
        return this;
    }

    public String getObservation() {
        return observation;
    }

    public ProductEntity withObservation(final String observation) {
        this.observation = observation;
        return this;
    }

    public Boolean isActive() {
        return active;
    }

    public ProductEntity withActive(final Boolean active) {
        this.active = active;
        return this;
    }

    public String getLastChangeTime() {
        return lastChangeTime;
    }

    public ProductEntity withLastChangeTime(final String lastChangeTime) {
        this.lastChangeTime = lastChangeTime;
        return this;
    }
}
