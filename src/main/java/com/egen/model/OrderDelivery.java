package com.egen.model;

import java.util.UUID;

public class OrderDelivery {

    private String deliveryId;
    private String deliveryMethod;
    private double deliveryCharges;

    public OrderDelivery() {
        this.deliveryId = UUID.randomUUID().toString();
    }

    public OrderDelivery(String deliveryMethod, double deliveryCharges) {
        this.deliveryId = UUID.randomUUID().toString();
        this.deliveryMethod = deliveryMethod;
        this.deliveryCharges = deliveryCharges;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public double getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(double deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    @Override
    public String toString() {
        return "OrderDelivery{" +
                "deliveryId='" + deliveryId + '\'' +
                ", deliveryMethod='" + deliveryMethod + '\'' +
                ", deliveryCharges=" + deliveryCharges +
                '}';
    }
}
