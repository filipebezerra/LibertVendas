package br.com.libertsolutions.libertvendas.app.domain.entity;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * @author Filipe Bezerra
 */
@RealmClass
public class PriceTableItemEntity implements RealmModel {

    @PrimaryKey
    private Integer itemId;

    @Required
    private Double salesPrice;

    private Integer productId;

    private ProductEntity product;

    private String lastChangeTime;

    public Integer getItemId() {
        return itemId;
    }

    public PriceTableItemEntity withItemId(final Integer itemId) {
        this.itemId = itemId;
        return this;
    }

    public Double getSalesPrice() {
        return salesPrice;
    }

    public PriceTableItemEntity withSalesPrice(final Double salesPrice) {
        this.salesPrice = salesPrice;
        return this;
    }

    public Integer getProductId() {
        return productId;
    }

    public PriceTableItemEntity withProductId(final Integer productId) {
        this.productId = productId;
        return this;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public PriceTableItemEntity withProduct(final ProductEntity product) {
        this.product = product;
        return this;
    }

    public String getLastChangeTime() {
        return lastChangeTime;
    }

    public PriceTableItemEntity withLastChangeTime(final String lastChangeTime) {
        this.lastChangeTime = lastChangeTime;
        return this;
    }
}
