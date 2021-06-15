package com.egen.model;

import java.util.UUID;

public class OrderItems {

    private String itemId;
    private String itemName;
    private int itemQty;
    private double subtotal;
    private double tax;
    private double total;

    public OrderItems() {
        super();
        this.itemId = UUID.randomUUID().toString();
    }

    public OrderItems(String itemName, int itemQty, double tax, double total) {
        super();
        this.itemId = UUID.randomUUID().toString();
        this.itemName = itemName;
        this.itemQty = itemQty;
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

    public static double sumOfItemTotal(OrderItems o1, OrderItems o2) {
        return o1.getSubtotal() + o2.getSubtotal();
    }

    @Override
    public String toString() {
        return "OrderItems{" +
                "itemName='" + itemName + '\'' +
                ", itemQty=" + itemQty +
                ", subtotal=" + subtotal +
                ", tax=" + tax +
                ", total=" + total +
                '}';
    }
}
