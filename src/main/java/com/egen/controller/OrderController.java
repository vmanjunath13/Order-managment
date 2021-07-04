package com.egen.controller;

import com.egen.model.dto.OrderDto;
import com.egen.model.entity.Order;
import com.egen.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping(path="/orders")
public class OrderController {

    @Autowired
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("order")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("order/{oid}")
    public void getOrderById(@PathVariable(name = "oid") String id) {
        System.out.println(orderService.getOrderById(id));
    }

    @GetMapping("orderInInterval/{startTime}/{endTime}")
    public ResponseEntity<List<Order>> getAllOrdersWithInInterval(@PathVariable Timestamp startTime, @PathVariable Timestamp endTime){
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

    @PostMapping("/cancel/{id}")
    public void cancelOrder(@PathVariable("id") String orderId){
        orderService.cancelOrder(orderId);
    }

    @PutMapping("/update")
    public ResponseEntity<Order> updateOrder(@RequestBody Order order){
        return ResponseEntity.ok(orderService.updateOrder(order.getOrderId(), order));
    }
}
