package com.egen.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "OrderItems")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String itemId;

    @Column(name = "itemName")
    private String itemName;

    @Column(name = "itemQty")
    private int itemQty;

    @Column(name = "subtotal")
    private double subtotal;

    @Column(name = "tax")
    private double tax;

    @Column(name = "total")
    private double total;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="order_id")
    private Order orders;

    public OrderItem() {
    }

    public OrderItem(String itemName, int itemQty, double subtotal, double tax, double total) {
        this.itemName = itemName;
        this.itemQty = itemQty;
        this.subtotal = subtotal;
        this.tax = tax;
        this.total = total;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getItemQty() {
        return itemQty;
    }

    public void setItemQty(Integer itemQty) {
        this.itemQty = itemQty;
        this.subtotal = this.total * this.itemQty;
        this.subtotal = this.subtotal + (this.tax/100)*this.subtotal;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
        this.subtotal = this.total * this.itemQty;
        this.subtotal = this.subtotal + (this.tax/100)*this.subtotal;
    }

    public static double sumOfItemTotal(OrderItem o1, OrderItem o2) {
        return o1.getSubtotal() + o2.getSubtotal();
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Order getOrders() {
        return orders;
    }

    public void setOrders(Order orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemQty=" + itemQty +
                ", subtotal=" + subtotal +
                ", tax=" + tax +
                ", total=" + total +
                ", orders=" + orders +
                '}';
    }
}
