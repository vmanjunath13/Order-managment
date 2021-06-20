package com.egen.controller;

import com.egen.model.Order;
import com.egen.repository.OrderRepository;
import com.egen.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

@RestController
public class OrderController {

   private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("order")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("order/{oid}")
    public ResponseEntity<Order> getOrderById(@PathVariable(name = "oid") String id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("orderInInterval/{startTime}/{endTime}")
    public ResponseEntity<List<Order>> getAllOrdersWithInInterval(@PathVariable ZonedDateTime startTime, @PathVariable ZonedDateTime endTime){
        return ResponseEntity.ok(orderService.getOrdersWithTimeInterval(startTime, endTime));
    }

    @GetMapping("top10OrdersInZip/{zip}")
    public ResponseEntity<List<Order>> top10OrdersWithHighestDollarAmountInZip(@PathVariable String zip) {
        return ResponseEntity.ok(orderService.top10OrdersWithHighestDollarAmountInZip(zip));
    }

    @PostMapping("/create")
    public ResponseEntity<Order> placeOrder(@RequestBody Order order){
        return new ResponseEntity<>(orderService.placeOrder(order), HttpStatus.CREATED);
    }

    @PutMapping("/cancel")
    public ResponseEntity<Order> cancelOrder(@RequestBody Order order){
        return ResponseEntity.ok(orderService.cancelOrder(order));
    }

    @PutMapping("/update")
    public ResponseEntity<Order> updateOrder(@RequestBody Order order){
        return ResponseEntity.ok(orderService.updateOrder(order));
    }
}
