package com.egen.service;

import com.egen.model.Order;

import java.time.ZonedDateTime;
import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();
    Order getOrderById(String orderId);
    List<Order> getOrdersWithTimeInterval(ZonedDateTime startTime, ZonedDateTime endTime);
    List<Order> top10OrdersWithHighestDollarAmountInZip(String zip);
    Order placeOrder(Order order);
    Order cancelOrder(Order order);
    Order updateOrder(Order order);
}
