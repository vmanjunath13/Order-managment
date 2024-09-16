package com.egen.service;

import com.egen.model.Order;
import com.egen.repository.OrderRepository;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.*;

@Service
public class OrderServiceImplementation implements OrderService {

    private OrderRepository orderRepository;

    public OrderServiceImplementation(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orderList = orderRepository.getOrders();
        return orderList;
    }

    @Override
    public Order getOrderById(String orderId) {
        return orderRepository.getOrderById(orderId);
    }

    @Override
    public List<Order> getOrdersWithTimeInterval(ZonedDateTime startTime, ZonedDateTime endTime) {
        return orderRepository.getOrdersWithTimeInterval(startTime.toString(), endTime.toString());
    }

    @Override
    public List<Order> top10OrdersWithHighestDollarAmountInZip(String zip) {
        List<Order> orderList = orderRepository.getOrders();
        List<Order> groceryOrdersInZip = new LinkedList<>();
        for(Order order: orderList){
            if(order.getShippingAddress().getPostalCode().equalsIgnoreCase(zip)) groceryOrdersInZip.add(order);
        }

        Collections.sort(groceryOrdersInZip, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return Double.compare(o2.getOrderTotal(), o1.getOrderTotal());
            }
        });

        List<Order> zipList = new ArrayList<>(10);
        for(int i=0 ; i < 10; i++){
            zipList.add(groceryOrdersInZip.get(i));
        }

        return zipList;
    }

    @Override
    public Order placeOrder(Order order) {
        return orderRepository.saveOrder(order);
    }

    @Override
    public Order cancelOrder(Order order) {
        return orderRepository.cancelOrder(order);
    }

    @Override
    public Order updateOrder(Order order) {
        return orderRepository.updateOrder(order);
    }
}
