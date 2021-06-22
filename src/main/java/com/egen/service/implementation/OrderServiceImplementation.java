package com.egen.service.implementation;

import com.egen.exception.BadRequestException;
import com.egen.exception.OrderServiceException;
import com.egen.model.entity.Order;
import com.egen.model.enums.OrderStatus;
import com.egen.repository.OrderRepository;
import com.egen.service.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Service
public class OrderServiceImplementation implements OrderService {

    final OrderRepository orderRepository;
    final OrderItemService orderItemService;
    final CustomerService customerService;
    final AddressService addressService;
    final OrderPaymentService paymentService;

    public OrderServiceImplementation(OrderRepository orderRepository, OrderItemService orderItemService, CustomerService customerService, AddressService addressService, OrderPaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.orderItemService = orderItemService;
        this.customerService = customerService;
        this.addressService = addressService;
        this.paymentService = paymentService;
    }

    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return Optional.ofNullable(orderRepository.findAll()).orElseThrow(
                () -> new OrderServiceException("Currently there are no orders! Place one right away!"));
    }

    @Transactional(readOnly = true)
    public Optional<Order> getOrderById(String orderId) {
        return Optional.ofNullable(orderRepository.findById(orderId)).orElseThrow(
                () -> new OrderServiceException("Currently there are no orders! Place one right away!"));
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersWithTimeInterval(Timestamp startTime, Timestamp endTime) {
        return  Optional.ofNullable(orderRepository.findAllByDateOrderedBetween(startTime, endTime))
                .orElseThrow(() ->
                        new OrderServiceException("Orders between the specific time range not found" ));
    }

    @Transactional(readOnly = true)
    public List<Order> top10OrdersWithHighestDollarAmountInZip(String zip) {
        return Optional.ofNullable(orderRepository.findAllByShippingAddressZipcode(zip))
                .orElseThrow(() -> new OrderServiceException("No orders were found in the zip code: "+zip));
    }

    @Transactional
    public Order placeOrder(Order order) {
        Optional<Order> orderCreate = orderRepository.findById(order.getOrderId());
        if(orderCreate.isPresent()) {
            throw new BadRequestException("Order for the given customer id " + order.getCustomer() + " with same order id " + order.getOrderId() + " already exists!");
        }
        return orderRepository.save(order);
    }

    @Transactional
    public Order cancelOrder(String id) {
        try {
            Optional<Order> orders = orderRepository.findById(id);
            if(!orders.isPresent())
                throw new OrderServiceException("Order not found!");
            orders.get().setStatus(OrderStatus.ORDER_CANCELLED);
            return orderRepository.save(orders.get());
        } catch (OrderServiceException ex) {
            throw new OrderServiceException("The order id: " + id +" your want to cancel is not found in our records");
        }
    }

    @Transactional
    public Order updateOrder(String id, Order order) {
        try {
            Optional<Order> orders = orderRepository.findById(id);
            if(!orders.isPresent())
                throw new OrderServiceException("Order not found!");
            orders.get().setStatus(order.getStatus());
            return orderRepository.save(orders.get());
        } catch (OrderServiceException ex) {
            throw new OrderServiceException("The order id: " + id +" you want to modify is not found in our records");
        }
    }

    @Transactional
    public List<Order> findAllByPageLimit(Integer pageNum, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNum, pageSize);
        return Optional.ofNullable(orderRepository.findAll(paging))
                .orElseThrow(() -> new OrderServiceException("No orders were found"))
                .getContent();
    }

    @Transactional
    public List<Order> sortByValues(Integer pageNum, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(sortBy));
        return Optional.ofNullable(orderRepository.findAll(paging))
                .orElseThrow(() -> new OrderServiceException("No orders were found based on the sort values"))
                .getContent();
    }
}
