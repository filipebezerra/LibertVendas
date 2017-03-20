package br.com.libertsolutions.libertvendas.app.domain.entity;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

import static br.com.libertsolutions.libertvendas.app.data.helper.RealmAutoIncrement.autoIncrementor;

/**
 * @author Filipe Bezerra
 */
@RealmClass
public class OrderItemEntity implements RealmModel {

    public static final class Fields {

        public static final String ID = "id";
    }

    @PrimaryKey
    private Integer id;

    private Integer orderItemId;

    private PriceTableItemEntity item;

    @Required
    private Float quantity;

    @Required
    private Double subTotal;

    private String lastChangeTime;

    public Integer getId() {
        return id;
    }

    public OrderItemEntity withId(final Integer id) {
        if (id == null || id == 0) {
            this.id = autoIncrementor(getClass(), Fields.ID).getNextIdFromModel();
        } else {
            this.id = id;
        }
        return this;
    }

    public Integer getOrderItemId() {
        return orderItemId;
    }

    public OrderItemEntity withOrderItemId(final Integer orderItemId) {
        this.orderItemId = orderItemId;
        return this;
    }

    public PriceTableItemEntity getItem() {
        return item;
    }

    public OrderItemEntity withItem(final PriceTableItemEntity item) {
        this.item = item;
        return this;
    }

    public Float getQuantity() {
        return quantity;
    }

    public OrderItemEntity withQuantity(final Float quantity) {
        this.quantity = quantity;
        return this;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public OrderItemEntity withSubTotal(final Double subTotal) {
        this.subTotal = subTotal;
        return this;
    }

    public String getLastChangeTime() {
        return lastChangeTime;
    }

    public OrderItemEntity withLastChangeTime(final String lastChangeTime) {
        this.lastChangeTime = lastChangeTime;
        return this;
    }
}
