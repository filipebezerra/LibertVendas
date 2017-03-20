package br.com.libertsolutions.libertvendas.app.domain.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Filipe Bezerra
 */
public class PriceTableItem {

    @Expose
    @SerializedName("idItensTabela")
    private Integer itemId;

    @Expose
    @SerializedName("prcVenda")
    private Double salesPrice;

    @Expose
    @SerializedName("idProduto")
    private Integer productId;

    @Expose
    @SerializedName("Produto")
    private Product product;

    @Expose
    @SerializedName("ultimaAlteracao")
    private String lastChangeTime;

    public Integer getItemId() {
        return itemId;
    }

    public PriceTableItem withItemId(final Integer itemId) {
        this.itemId = itemId;
        return this;
    }

    public Double getSalesPrice() {
        return salesPrice;
    }

    public PriceTableItem withSalesPrice(final Double salesPrice) {
        this.salesPrice = salesPrice;
        return this;
    }

    public Integer getProductId() {
        return productId;
    }

    public PriceTableItem withProductId(final Integer productId) {
        this.productId = productId;
        return this;
    }

    public Product getProduct() {
        return product;
    }

    public PriceTableItem withProduct(
            final Product product) {
        this.product = product;
        return this;
    }

    public String getLastChangeTime() {
        return lastChangeTime;
    }

    public PriceTableItem withLastChangeTime(final String lastChangeTime) {
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

        PriceTableItem that = (PriceTableItem) o;

        return getItemId().equals(that.getItemId());
    }

    @Override public int hashCode() {
        return getItemId().hashCode();
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("PriceTableItem{");
        sb.append("itemId=").append(itemId);
        sb.append(", salesPrice=").append(salesPrice);
        sb.append(", productId=").append(productId);
        sb.append(", product=").append(product);
        sb.append(", lastChangeTime='").append(lastChangeTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
