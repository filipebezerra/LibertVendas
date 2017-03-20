package br.com.libertsolutions.libertvendas.app.domain.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class PriceTable {

    @Expose
    @SerializedName("idTabela")
    private Integer priceTableId;

    @Expose
    @SerializedName("codigo")
    private String code;

    @Expose
    @SerializedName("nome")
    private String name;

    @Expose
    @SerializedName("ItensTabela")
    private List<PriceTableItem> mItems;

    @Expose
    @SerializedName("ativo")
    private Boolean active;

    @Expose
    @SerializedName("ultimaAlteracao")
    private String lastChangeTime;

    public Integer getPriceTableId() {
        return priceTableId;
    }

    public PriceTable withPriceTableId(final Integer priceTableId) {
        this.priceTableId = priceTableId;
        return this;
    }

    public String getCode() {
        return code;
    }

    public PriceTable withCode(final String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public PriceTable withName(final String name) {
        this.name = name;
        return this;
    }

    public List<PriceTableItem> getItems() {
        return mItems;
    }

    public PriceTable withItems(final List<PriceTableItem> items) {
        mItems = items;
        return this;
    }

    public Boolean isActive() {
        return active;
    }

    public PriceTable withActive(final Boolean active) {
        this.active = active;
        return this;
    }

    public String getLastChangeTime() {
        return lastChangeTime;
    }

    public PriceTable withLastChangeTime(final String lastChangeTime) {
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

        PriceTable that = (PriceTable) o;

        return getPriceTableId().equals(that.getPriceTableId());
    }

    @Override public int hashCode() {
        return getPriceTableId().hashCode();
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("PriceTable{");
        sb.append("priceTableId=").append(priceTableId);
        sb.append(", code='").append(code).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", mItems=").append(mItems);
        sb.append(", active=").append(active);
        sb.append(", lastChangeTime='").append(lastChangeTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
