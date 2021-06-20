package com.egen.service;

import com.egen.exception.BadRequestException;
import com.egen.exception.ResourceNotFoundException;
import com.egen.model.Order;
import com.egen.model.OrderStatus;
import com.egen.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Service
public class OrderServiceImplementation implements OrderService {

    @Autowired
    private final OrderRepository orderRepository;

    public OrderServiceImplementation(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return (List<Order>) orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Order getOrderById(String orderId) {
        Optional<Order> orderById = orderRepository.findById(orderId);
        if(!orderById.isPresent()) {
            throw new ResourceNotFoundException("Order with id " + orderId + " doesn't exist!");
        }
        return orderById.get();
    }

    @Override
    public List<Order> getOrdersWithTimeInterval(Timestamp startTime, Timestamp endTime) {
        return orderRepository.findByStartDateBetween(startTime, endTime);
    }

    @Override
    public List<Order> top10OrdersWithHighestDollarAmountInZip(String zip) {
        List<Order> orderList = (List<Order>) orderRepository.findAll();
        List<Order> ordersInZip = new LinkedList<>();
        for(Order order: orderList){
            if(order.getShippingAddress().getPostalCode().equalsIgnoreCase(zip)) ordersInZip.add(order);
        }

        Collections.sort(ordersInZip, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return Double.compare(o2.getOrderTotal(), o1.getOrderTotal());
            }
        });

        List<Order> zipList = new ArrayList<>(10);
        for(int i=0 ; i < 10; i++){
            zipList.add(ordersInZip.get(i));
        }

        return zipList;
    }

    @Transactional
    public Order placeOrder(Order order) {
        Optional<Order> orderCreate = orderRepository.findByCustomerId(order.getCustomer_id(), order.getOrderId());
        if(orderCreate.isPresent()) {
            throw new BadRequestException("Order for the given customer id " + order.getCustomer_id() + " with same order id " + order.getOrderId() + " already exists!");
        }
        return orderRepository.save(order);
    }

    @Transactional
    public void cancelOrder(String id) {
        Optional<Order> orderDelete = orderRepository.findById(id);
        orderDelete.get().setStatus(OrderStatus.ORDER_CANCELLED);
        if (!orderDelete.isPresent()) {
            throw new ResourceNotFoundException("Order with id " + id + " doesn't exists");
        }
        orderRepository.delete(orderDelete.get());
    }

    @Transactional
    public Order updateOrder(String id, Order order) {
        Optional<Order> orderUpdate = orderRepository.findById(id);
        if (!orderUpdate.isPresent()) {
            throw new ResourceNotFoundException("Order with id " + id + " doesn't exists");
        }
        return orderRepository.save(order);
    }
}
