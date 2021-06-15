package com.egen.repository;

import com.egen.model.*;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class OrderRepository {
    private List<Order> orderList;

    public OrderRepository() {
        this.orderList = new ArrayList<>();
        List<OrderItems> orderItems = new ArrayList<>();
        orderItems.add(new OrderItems("iPad", 1, 13.45, 1299.99));
        orderItems.add(new OrderItems("Monitor", 2, 22.38, 679.99));
        orderItems.add(new OrderItems("NoteBook", 5, 2.38, 29));
        Address billingAddress = new Address("2901 S King Dr", "1301", "Chicago", "Illinois", 60616);
        Address shippingAddress = new Address("3001 S King Dr", "1802", "Chicago", "Illinois", 60616);
        List<OrderPayment> orderPayments = new ArrayList<>();
        orderPayments.add(new OrderPayment("Credit Card", ZonedDateTime.now(), UUID.randomUUID().toString(), billingAddress));
        orderPayments.add(new OrderPayment("Paypal", ZonedDateTime.now(), UUID.randomUUID().toString(), billingAddress));
        orderPayments.add(new OrderPayment("Credit Card", ZonedDateTime.now(), UUID.randomUUID().toString(), billingAddress));
        OrderDelivery orderDelivery = new OrderDelivery("Home delivery", 2.99);

        Order order = new Order(OrderStatus.ORDER_IN_PROGRESS, UUID.randomUUID().toString(), orderItems, orderPayments, shippingAddress, ZonedDateTime.now(), ZonedDateTime.now(), orderDelivery);
        this.orderList.add(order);

        orderItems = new ArrayList<>();
        orderItems.add(new OrderItems("Car Toy", 2, 13.45, 99.99));
        orderItems.add(new OrderItems("Pen", 1, 1.15, 8.99));
        orderPayments = new ArrayList<>();
        orderPayments.add(new OrderPayment("Credit Card", ZonedDateTime.now(), UUID.randomUUID().toString(), billingAddress));
        orderPayments.add(new OrderPayment("Paypal", ZonedDateTime.now(), UUID.randomUUID().toString(), billingAddress));
        orderDelivery = new OrderDelivery("In-Store Pickup", 0);
        order = new Order(OrderStatus.ORDER_IN_PROGRESS, UUID.randomUUID().toString(), orderItems, orderPayments, shippingAddress, ZonedDateTime.now(), ZonedDateTime.now(), orderDelivery);
        this.orderList.add(order);

    }

    public List<Order> getOrders() {
        return orderList;
    }

    public void setOrders(List<Order> orderList) {
        this.orderList = orderList;
    }

    public Order getOrderById(String id) {
        for (Order order : orderList) {
            if(order.getId().equals(id))
                return order;
        }
        return null;
    }

    public List<Order> getOrdersWithTimeInterval(String startTime, String endTime) {
        ZonedDateTime sTime = ZonedDateTime.parse(startTime + "-05:00[America/Chicago]");
        ZonedDateTime eTime = ZonedDateTime.parse(endTime + "-05:00[America/Chicago]");

        return this.orderList.stream().filter(order -> order.getDateCreated().hashCode() >= sTime.hashCode() && order.getDateCreated().hashCode() < eTime.hashCode())
                .collect(Collectors.toList());
    }
}
