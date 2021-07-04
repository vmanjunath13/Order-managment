package com.egen.model.entity;

import com.egen.model.enums.PaymentMethod;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

@Entity
@Data
@Table(name = "OrderPayment")
public class OrderPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String paymentId;
    @Column(name = "paymentmethod")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @Column(name = "paymentdate")
    private Timestamp paymentDate;
    @Column(name = "paymentconfirmationnumber")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String paymentConfirmationNumber;
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "addressId")
    private Address billingAddress;

    public OrderPayment() {

    }

    public OrderPayment(Timestamp paymentDate, PaymentMethod paymentMethod, Address address) {
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.billingAddress = address;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentDate() {
        return paymentDate.toString();
    }

    public void setPaymentDate(Timestamp paymentDate) {
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
