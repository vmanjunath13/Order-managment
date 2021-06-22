package com.egen.model.entity;

import com.egen.model.enums.DeliveryMethod;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "OrderDelivery")
public class OrderDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String deliveryId;
    @Enumerated(EnumType.STRING)
    @Column(name = "deliveryMethod")
    private DeliveryMethod deliveryMethod;
    @Column(name = "deliveryCharges")
    private double deliveryCharges;

    public OrderDelivery() {

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
