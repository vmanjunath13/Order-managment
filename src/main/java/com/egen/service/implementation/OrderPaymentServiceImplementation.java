package com.egen.service.implementation;

import com.egen.exception.PaymentServiceException;
import com.egen.model.entity.Address;
import com.egen.model.entity.Order;
import com.egen.model.entity.OrderPayment;
import com.egen.repository.OrderPaymentRepository;
import com.egen.service.OrderPaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class OrderPaymentServiceImplementation implements OrderPaymentService {

    final OrderPaymentRepository orderPaymentRepository;

    public OrderPaymentServiceImplementation(OrderPaymentRepository orderPaymentRepository) {
        this.orderPaymentRepository = orderPaymentRepository;
    }

    @Transactional
    public List<OrderPayment> createPayment(List<OrderPayment> orderPayment, Address address, Order order) {
        try {
            Date date = new Date();
            Timestamp paymentDate = new Timestamp(date.getTime());
            orderPayment.forEach(pay -> {
                OrderPayment ord = new OrderPayment(paymentDate, pay.getPaymentMethod(), address);
                orderPaymentRepository.save(ord);
            });
            return orderPayment;
        } catch(Exception e) {
            throw new PaymentServiceException("Failed to process the payment transaction");
        }
    }
}
