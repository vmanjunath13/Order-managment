package com.egen.controller;

import com.egen.model.Order;
import com.egen.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

@RestController
public class OrderController {
    /**
     * implement the following endpoints
     */

    OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("order")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(this.orderRepository.getOrders());
    }

    @GetMapping("order/{oid}")
    public ResponseEntity<Order> getOrderById(@PathVariable(name = "oid") String id) {
        return ResponseEntity.ok(this.orderRepository.getOrderById(id));
    }

    @GetMapping(value = "order", params = { "startTime", "endTime" })
    public ResponseEntity<List<Order>> getAllOrdersWithInInterval(@RequestParam("startTime") ZonedDateTime startTime, @RequestParam("endTime") ZonedDateTime endTime){
        return ResponseEntity.ok(this.orderRepository.getOrdersWithTimeInterval(startTime.toString(), endTime.toString()));
    }

    @GetMapping(value = "order", params = {"zip"})
    public ResponseEntity<List<Order>> top10OrdersWithHighestDollarAmountInZip(@RequestParam("zip") String zip) {
        return ResponseEntity.ok(this.orderRepository.getOrders());
    }

    public ResponseEntity<Order> placeOrder(Order order){
        return null;
    }

    public ResponseEntity<Order> cancelOrder(Order order){
        return null;
    }

    public ResponseEntity<Order> updateOrder(Order order){
        return null;
    }
}
