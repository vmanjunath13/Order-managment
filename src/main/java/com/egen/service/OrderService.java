package com.egen.service;

import com.egen.model.Order;

import java.sql.Timestamp;
import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();
    Order getOrderById(String orderId);
    List<Order> getOrdersWithTimeInterval(Timestamp startTime, Timestamp endTime);
    List<Order> top10OrdersWithHighestDollarAmountInZip(String zip);
    Order placeOrder(Order order);
    void cancelOrder(String id);
    Order updateOrder(String id, Order order);
}
