package com.egen.service;

import com.egen.model.entity.Order;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Order> getAllOrders();
    Optional<Order> getOrderById(String orderId);
    List<Order> getOrdersWithTimeInterval(Timestamp startTime, Timestamp endTime);
    List<Order> top10OrdersWithHighestDollarAmountInZip(String zip);
    Order placeOrder(Order order);
    Order cancelOrder(String id);
    Order updateOrder(String id, Order order);
    List<Order> findAllByPageLimit(Integer pageNum, Integer pageSize);
    List<Order> sortByValues(Integer pageNum, Integer pageSize, String sortBy);
}
