package com.egen.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
public class Order {

    @Id
    private String orderId;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private String customer_id;
    @OneToMany(cascade = {CascadeType.ALL})
    private List<OrderItems> orderItemsList;
    private double orderTotal;
    @OneToMany(cascade = {CascadeType.ALL})
    private List<OrderPayment> orderPayment;
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private Address shippingAddress;
    private Timestamp dateCreated;
    private Timestamp dateUpdated;
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private OrderDelivery orderDelivery;

    public Order() {
        super();
        this.orderId = UUID.randomUUID().toString();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String id) {
        this.orderId = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public List<OrderItems> getOrderItemsList() {
        return orderItemsList;
    }

    public void setOrderItemsList(List<OrderItems> orderItemsList) {
        this.orderItemsList = orderItemsList;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public List<OrderPayment> getOrderPayment() {
        return orderPayment;
    }

    public void setOrderPayment(List<OrderPayment> orderPayment) {
        this.orderPayment = orderPayment;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getDateCreated() {
        return dateCreated.toString();
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateUpdated() {
        return dateUpdated.toString();
    }

    public void setDateUpdated(Timestamp dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public OrderDelivery getOrderDelivery() {
        return orderDelivery;
    }

    public void setOrderDelivery(OrderDelivery orderDelivery) {
        this.orderDelivery = orderDelivery;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + orderId + '\'' +
                ", status=" + status +
                ", customer_id='" + customer_id + '\'' +
                ", orderItemsList=" + orderItemsList +
                ", orderTotal=" + orderTotal +
                ", orderPayment=" + orderPayment +
                ", shippingAddress=" + shippingAddress +
                ", dateCreated=" + dateCreated +
                ", dateUpdated=" + dateUpdated +
                ", orderDelivery=" + orderDelivery +
                '}';
    }
}
