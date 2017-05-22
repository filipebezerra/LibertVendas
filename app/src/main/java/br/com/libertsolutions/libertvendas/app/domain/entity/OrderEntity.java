package br.com.libertsolutions.libertvendas.app.domain.entity;

import br.com.libertsolutions.libertvendas.app.domain.pojo.OrderStatus;
import br.com.libertsolutions.libertvendas.app.domain.pojo.OrderType;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

import static br.com.libertsolutions.libertvendas.app.data.helper.RealmAutoIncrement.autoIncrementor;
import static br.com.libertsolutions.libertvendas.app.domain.pojo.OrderStatus.STATUS_SYNCED;

/**
 * @author Filipe Bezerra
 */
@RealmClass
public class OrderEntity implements RealmModel {

    public static final class Fields {

        public static final String ID = "id";

        public static final String ORDER_ID = "orderId";

        public static final String ISSUE_DATE = "issueDate";

        public static final String STATUS = "status";

        public static final String SALESMAN_ID = "salesmanId";

        public static final String COMPANY_ID = "companyId";

        public static final String CUSTOMER_NAME = "customer." + CustomerEntity.Fields.NAME;
    }

    @PrimaryKey
    private Integer id;

    private Integer orderId;

    @Required
    private Integer type;

    @Required
    private Long issueDate;

    private Double discount;

    private Float discountPercentage;

    private String observation;

    private CustomerEntity customer;

    private PaymentMethodEntity paymentMethod;

    private PriceTableEntity priceTable;

    private RealmList<OrderItemEntity> items;

    private String lastChangeTime;

    @Required
    private Integer salesmanId;

    @Required
    private Integer companyId;

    @Required
    private Integer status;

    public Integer getId() {
        return id;
    }

    public OrderEntity withId(final Integer id) {
        if (id == null || id == 0) {
            this.id = autoIncrementor(getClass(), Fields.ID).getNextIdFromModel();
        } else {
            this.id = id;
        }
        return this;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public OrderEntity withOrderId(final Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    @OrderType public Integer getType() {
        return type;
    }

    public OrderEntity withType(@OrderType final Integer type) {
        this.type = type;
        return this;
    }

    public Long getIssueDate() {
        return issueDate;
    }

    public OrderEntity withIssueDate(final Long issueDate) {
        this.issueDate = issueDate;
        return this;
    }

    public Double getDiscount() {
        return discount;
    }

    public OrderEntity withDiscount(final Double discount) {
        this.discount = discount;
        return this;
    }

    public Float getDiscountPercentage() {
        return discountPercentage;
    }

    public OrderEntity withDiscountPercentage(final Float discountPercentage) {
        this.discountPercentage = discountPercentage;
        return this;
    }

    public String getObservation() {
        return observation;
    }

    public OrderEntity withObservation(final String observation) {
        this.observation = observation;
        return this;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public OrderEntity withCustomer(final CustomerEntity customer) {
        this.customer = customer;
        return this;
    }

    public PaymentMethodEntity getPaymentMethod() {
        return paymentMethod;
    }

    public OrderEntity withPaymentMethod(final PaymentMethodEntity paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public PriceTableEntity getPriceTable() {
        return priceTable;
    }

    public OrderEntity withPriceTable(final PriceTableEntity priceTable) {
        this.priceTable = priceTable;
        return this;
    }

    public RealmList<OrderItemEntity> getItems() {
        return items;
    }

    public OrderEntity withItems(final RealmList<OrderItemEntity> items) {
        this.items = items;
        return this;
    }

    public String getLastChangeTime() {
        return lastChangeTime;
    }

    public OrderEntity withLastChangeTime(final String lastChangeTime) {
        this.lastChangeTime = lastChangeTime;
        return this;
    }

    public Integer getSalesmanId() {
        return salesmanId;
    }

    public OrderEntity withSalesmanId(final Integer salesmanId) {
        this.salesmanId = salesmanId;
        return this;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public OrderEntity withCompanyId(final Integer companyId) {
        this.companyId = companyId;
        return this;
    }

    @OrderStatus public Integer getStatus() {
        return status;
    }

    public OrderEntity withStatus(@OrderStatus final Integer status) {
        if (status == null) {
            this.status = STATUS_SYNCED;
        } else {
            this.status = status;
        }
        return this;
    }
}
