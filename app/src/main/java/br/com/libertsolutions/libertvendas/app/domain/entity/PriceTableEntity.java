package br.com.libertsolutions.libertvendas.app.domain.entity;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * @author Filipe Bezerra
 */
@RealmClass
public class PriceTableEntity implements RealmModel {

    public static final class Fields {

        public static final String PRICE_TABLE_ID = "priceTableId";
    }

    @PrimaryKey
    private Integer priceTableId;

    private String code;

    private String name;

    private RealmList<PriceTableItemEntity> mItems;

    private Boolean active;

    private String lastChangeTime;

    public Integer getPriceTableId() {
        return priceTableId;
    }

    public PriceTableEntity withPriceTableId(final Integer priceTableId) {
        this.priceTableId = priceTableId;
        return this;
    }

    public String getCode() {
        return code;
    }

    public PriceTableEntity withCode(final String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public PriceTableEntity withName(final String name) {
        this.name = name;
        return this;
    }

    public RealmList<PriceTableItemEntity> getItems() {
        return mItems;
    }

    public PriceTableEntity withItems(final RealmList<PriceTableItemEntity> items) {
        mItems = items;
        return this;
    }

    public Boolean isActive() {
        return active;
    }

    public PriceTableEntity withActive(final Boolean active) {
        this.active = active;
        return this;
    }

    public String getLastChangeTime() {
        return lastChangeTime;
    }

    public PriceTableEntity withLastChangeTime(final String lastChangeTime) {
        this.lastChangeTime = lastChangeTime;
        return this;
    }
}
