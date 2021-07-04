package com.egen.service;

import com.egen.exception.PaymentServiceException;
import com.egen.model.entity.Address;
import com.egen.model.entity.Order;
import com.egen.model.entity.OrderPayment;
import com.egen.model.enums.PaymentMethod;
import com.egen.repository.OrderPaymentRepository;
import com.egen.service.implementation.OrderPaymentServiceImplementation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PaymentServiceImplTest {

    @Bean
    public OrderPaymentService getService() {
        return new OrderPaymentServiceImplementation();
    }

    @Autowired
    OrderPaymentService paymentService;

    @MockBean
    OrderPaymentRepository paymentRepository;

    OrderPayment orderPayment;
    List<OrderPayment> paymentList;
    Address addresses;
    Order new_order;

    @Before
    public void setUp(){
        orderPayment = new OrderPayment();
        new_order = new Order();
        addresses = new Address();
        paymentList = new ArrayList<>();
        addresses.setAddressId(UUID.randomUUID().toString());
        new_order.setOrderId(UUID.randomUUID().toString());

        orderPayment.setPaymentId(UUID.randomUUID().toString());
        orderPayment.setPaymentConfirmationNumber(UUID.randomUUID().toString());
        orderPayment.setPaymentDate(Timestamp.valueOf(LocalDateTime.now()));
        orderPayment.setPaymentMethod(PaymentMethod.CREDIT);

        paymentList.add(orderPayment);

        orderPayment.setPaymentId(UUID.randomUUID().toString());
        orderPayment.setPaymentConfirmationNumber(UUID.randomUUID().toString());
        orderPayment.setPaymentDate(Timestamp.valueOf(LocalDateTime.now()));
        orderPayment.setPaymentMethod(PaymentMethod.PAYPAL);

        paymentList.add(orderPayment);

        Mockito.when(paymentRepository.save(orderPayment)).thenReturn(orderPayment);
    }

    @After
    public void cleanUp(){
        System.out.println("Payment Service testing completed");
    }

    @Test
    public void createPayment() {
        List<OrderPayment> new_payments= paymentService.createPayment(paymentList, addresses, new_order);
        Assert.assertEquals("failed to make payment", paymentList, new_payments);
    }
    @Test(expected = PaymentServiceException.class)
    public void createPaymentFailed() {
        List<OrderPayment> new_payments= paymentService.createPayment(paymentList, addresses, new_order);
        Assert.assertNull("failed to make payment", new_payments);
    }
}
