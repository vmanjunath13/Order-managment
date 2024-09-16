package com.egen.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class OrderDelivery {

    @Id
    private String deliveryId;
    @Enumerated(EnumType.STRING)
    private DeliveryMethod deliveryMethod;
    private double deliveryCharges;

    public OrderDelivery() {
        this.deliveryId = UUID.randomUUID().toString();
    }

    public OrderDelivery(double deliveryCharges) {
        this.deliveryId = UUID.randomUUID().toString();
        this.deliveryCharges = deliveryCharges;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
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
