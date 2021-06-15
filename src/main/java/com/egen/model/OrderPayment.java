package com.egen.model;

import java.time.ZonedDateTime;
import java.util.UUID;

public class OrderPayment {

    private String paymentId;
    private String paymentMethod;
    private ZonedDateTime paymentDate;
    private String paymentConfirmationNumber;
    private Address billingAddress;

    public OrderPayment() {
        this.paymentId = UUID.randomUUID().toString();
    }

    public OrderPayment(String paymentMethod, ZonedDateTime paymentDate, String paymentConfirmationNumber, Address billingAddress) {
        super();
        this.paymentId = UUID.randomUUID().toString();
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.paymentConfirmationNumber = paymentConfirmationNumber;
        this.billingAddress = billingAddress;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentDate() {
        return paymentDate.toString();
    }

    public void setPaymentDate(ZonedDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentConfirmationNumber() {
        return paymentConfirmationNumber;
    }

    public void setPaymentConfirmationNumber(String paymentConfirmationNumber) {
        this.paymentConfirmationNumber = paymentConfirmationNumber;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    @Override
    public String toString() {
        return "OrderPayment{" +
                "paymentId='" + paymentId + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentDate=" + paymentDate +
                ", paymentConfirmationNumber='" + paymentConfirmationNumber + '\'' +
                ", billingAddress=" + billingAddress +
                '}';
    }
}
