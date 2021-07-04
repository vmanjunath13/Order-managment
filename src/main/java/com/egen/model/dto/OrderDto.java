package com.egen.model.dto;

import com.egen.model.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDto {

    private String orderId;

    @JsonProperty(value = "status")
    private OrderStatus status;

    @JsonProperty(value = "customer")
    private CustomerDto customer;

    private List<OrderItemDto> orderItemsList;

    @JsonProperty(value = "orderTotal")
    private double orderTotal;

    private List<OrderPaymentDto> orderPayment;

    @JsonProperty(value = "shippingAddress")
    private AddressDto shippingAddress;
    @JsonProperty(value = "billingAddress")
    private AddressDto billingAddress;

    @JsonProperty(value = "dateCreated")
    private Timestamp dateCreated;

    @JsonProperty(value = "dateUpdated")
    private Timestamp dateUpdated;

    private OrderDeliveryDto orderDelivery;

    public OrderDto() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public List<OrderItemDto> getOrderItemsList() {
        return orderItemsList;
    }

    public void setOrderItemsList(List<OrderItemDto> orderItemsList) {
        this.orderItemsList = orderItemsList;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public List<OrderPaymentDto> getOrderPayment() {
        return orderPayment;
    }

    public void setOrderPayment(List<OrderPaymentDto> orderPayment) {
        this.orderPayment = orderPayment;
    }

    public AddressDto getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(AddressDto shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public AddressDto getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(AddressDto billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Timestamp getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Timestamp dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public OrderDeliveryDto getOrderDelivery() {
        return orderDelivery;
    }

    public void setOrderDelivery(OrderDeliveryDto orderDelivery) {
        this.orderDelivery = orderDelivery;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "orderId='" + orderId + '\'' +
                ", status=" + status +
                ", customer='" + customer + '\'' +
                ", orderItemsList=" + orderItemsList +
                ", orderTotal=" + orderTotal +
                ", orderPayment=" + orderPayment +
                ", shippingAddress=" + shippingAddress +
                ", billingAddress=" + billingAddress +
                ", dateCreated=" + dateCreated +
                ", dateUpdated=" + dateUpdated +
                ", orderDelivery=" + orderDelivery +
                '}';
    }
}
