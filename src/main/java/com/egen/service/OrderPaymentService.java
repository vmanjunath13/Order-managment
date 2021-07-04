package com.egen.service;

import com.egen.model.entity.Address;
import com.egen.model.entity.Order;
import com.egen.model.entity.OrderPayment;

import java.util.List;

public interface OrderPaymentService {

    List<OrderPayment> createPayment(List<OrderPayment> orderPayment, Address address, Order order);
}
