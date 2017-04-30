package br.com.libertsolutions.libertvendas.app.domain.pojo;

import br.com.libertsolutions.libertvendas.app.domain.dto.OrderDto;
import br.com.libertsolutions.libertvendas.app.domain.dto.OrderItemDto;
import br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils;
import java.util.ArrayList;
import java.util.List;

import static br.com.libertsolutions.libertvendas.app.presentation.util.DateUtils.getCurrentDateTimeInMillis;

/**
 * @author Filipe Bezerra
 */
public class Order {

    private Integer id;

    private Integer orderId;

    private Integer type;

    private Long issueDate;

    private Double discount;

    private String observation;

    private Customer customer;

    private PaymentMethod paymentMethod;

    private PriceTable priceTable;

    private List<OrderItem> items;

    private String lastChangeTime;

    private Integer salesmanId;

    private Integer companyId;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public Order withId(final Integer id) {
        this.id = id;
        return this;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public Order withOrderId(final Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    @OrderType public Integer getType() {
        return type;
    }

    public Order withType(@OrderType final Integer type) {
        this.type = type;
        return this;
    }

    public Long getIssueDate() {
        return issueDate;
    }

    public Order withIssueDate(final Long issueDate) {
        this.issueDate = issueDate;
        return this;
    }

    public Double getDiscount() {
        return discount;
    }

    public Order withDiscount(final Double discount) {
        this.discount = discount;
        return this;
    }

    public String getObservation() {
        return observation;
    }

    public Order withObservation(final String observation) {
        this.observation = observation;
        return this;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Order withCustomer(final Customer customer) {
        this.customer = customer;
        return this;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public Order withPaymentMethod(final PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public PriceTable getPriceTable() {
        return priceTable;
    }

    public Order withPriceTable(final PriceTable priceTable) {
        this.priceTable = priceTable;
        return this;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public Order withItems(final List<OrderItem> items) {
        this.items = items;
        return this;
    }

    public double getTotalItems() {
        double totalItems = 0;
        for (OrderItem item : getItems())
            totalItems += item.getSubTotal();
        return totalItems;
    }

    public String getLastChangeTime() {
        return lastChangeTime;
    }

    public Order withLastChangeTime(final String lastChangeTime) {
        this.lastChangeTime = lastChangeTime;
        return this;
    }

    public Integer getSalesmanId() {
        return salesmanId;
    }

    public Order withSalesmanId(final Integer salesmanId) {
        this.salesmanId = salesmanId;
        return this;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public Order withCompanyId(final Integer companyId) {
        this.companyId = companyId;
        return this;
    }

    @OrderStatus public Integer getStatus() {
        return status;
    }

    public Order withStatus(@OrderStatus final Integer status) {
        this.status = status;
        return this;
    }

    public boolean isStatusCreatedOrModified() {
        return status == OrderStatus.STATUS_CREATED || status == OrderStatus.STATUS_MODIFIED;
    }

    public boolean isStatusSyncedOrCancelled() {
        return status == OrderStatus.STATUS_SYNCED || status == OrderStatus.STATUS_CANCELLED;
    }

    public boolean isStatusEquals(final Order order) {
        return isStatusCreatedOrModified() && order.isStatusCreatedOrModified() ||
                isStatusSyncedOrCancelled() && order.isStatusSyncedOrCancelled();
    }

    @Override public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Order order = (Order) o;

        if (!getId().equals(order.getId())) {
            return false;
        }
        if (!getSalesmanId().equals(order.getSalesmanId())) {
            return false;
        }
        return getCompanyId().equals(order.getCompanyId());
    }

    @Override public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getSalesmanId().hashCode();
        result = 31 * result + getCompanyId().hashCode();
        return result;
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("id=").append(id);
        sb.append(", orderId=").append(orderId);
        sb.append(", type=").append(type);
        sb.append(", issueDate=").append(issueDate);
        sb.append(", discount=").append(discount);
        sb.append(", observation='").append(observation).append('\'');
        sb.append(", customer=").append(customer);
        sb.append(", paymentMethod=").append(paymentMethod);
        sb.append(", priceTable=").append(priceTable);
        sb.append(", items=").append(items);
        sb.append(", lastChangeTime='").append(lastChangeTime).append('\'');
        sb.append(", salesmanId=").append(salesmanId);
        sb.append(", companyId=").append(companyId);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }

    public Order copy() {
        return new Order()
                .withType(getType())
                .withIssueDate(getCurrentDateTimeInMillis())
                .withDiscount(getDiscount())
                .withObservation(getObservation())
                .withCustomer(getCustomer())
                .withPaymentMethod(getPaymentMethod())
                .withPriceTable(getPriceTable())
                .withItems(getItems())
                .withSalesmanId(getSalesmanId())
                .withCompanyId(getCompanyId())
                .withStatus(OrderStatus.STATUS_CREATED);
    }

    public OrderDto createPostOrder() {
        List<OrderItemDto> items = new ArrayList<>();
        for (OrderItem item : getItems()) {
            items.add(item.createPostOrderItem());
        }

        return new OrderDto(
                getId(),
                getType(),
                FormattingUtils.formatAsISODateTime(getIssueDate()),
                getDiscount() != null ? getDiscount() : 0,
                getObservation(),
                getCustomer().getCustomerId(),
                getPaymentMethod().getPaymentMethodId(),
                getPriceTable().getPriceTableId(),
                items
        );
    }
}
