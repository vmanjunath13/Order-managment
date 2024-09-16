package com.egen.model;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class Order {

    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private String customer_id;
    @OneToMany(cascade = {CascadeType.ALL})
    private List<OrderItems> orderItemsList;
    private double orderTotal;
    @OneToMany(cascade = {CascadeType.ALL})
    private List<OrderPayment> orderPayment;
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private Address shippingAddress;
    private ZonedDateTime dateCreated;
    private ZonedDateTime dateUpdated;
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private OrderDelivery orderDelivery;

    public Order() {
        this.id = UUID.randomUUID().toString();
    }

    public Order(OrderStatus status, String customer_id, List<OrderItems> orderItemsList, List<OrderPayment> orderPayment, Address shippingAddress, ZonedDateTime dateCreated, ZonedDateTime dateUpdated, OrderDelivery orderDelivery) {
        this.id = UUID.randomUUID().toString();;
        this.status = status;
        this.customer_id = customer_id;
        this.orderItemsList = orderItemsList;
        this.orderPayment = orderPayment;
        this.shippingAddress = shippingAddress;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.orderDelivery = orderDelivery;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateUpdated() {
        return dateUpdated.toString();
    }

    public void setDateUpdated(ZonedDateTime dateUpdated) {
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
                "id='" + id + '\'' +
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
