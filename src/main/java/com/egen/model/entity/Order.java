package com.egen.model.entity;

import com.egen.model.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "Order")
@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String orderId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne(mappedBy = "order", cascade = {CascadeType.ALL})
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.ALL})
    private List<OrderItem> orderItemList;

    @Column(name = "orderTotal")
    private double orderTotal;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.ALL})
    private List<OrderPayment> orderPayment;

    @OneToOne(mappedBy = "order", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private Address shippingAddress;

    @Column(name = "dateCreated")
    private Timestamp dateCreated;

    @Column(name = "dateUpdated")
    private Timestamp dateUpdated;

    @OneToOne(mappedBy = "order", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private OrderDelivery orderDelivery;

    public Order() {
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public void setOrderPayment(List<OrderPayment> orderPayment) {
        this.orderPayment = orderPayment;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDateUpdated(Timestamp dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public void setOrderDelivery(OrderDelivery orderDelivery) {
        this.orderDelivery = orderDelivery;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + orderId + '\'' +
                ", status=" + status +
                ", customer='" + customer + '\'' +
                ", orderItemsList=" + orderItemList +
                ", orderTotal=" + orderTotal +
                ", orderPayment=" + orderPayment +
                ", shippingAddress=" + shippingAddress +
                ", dateCreated=" + dateCreated +
                ", dateUpdated=" + dateUpdated +
                ", orderDelivery=" + orderDelivery +
                '}';
    }
}
