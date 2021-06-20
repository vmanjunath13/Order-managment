package com.egen.repository;

import com.egen.model.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Transactional
public class OrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

//    private List<Order> orderList;
//
//    public OrderRepository() {
//        this.orderList = new ArrayList<>();
//        List<OrderItems> orderItems = new ArrayList<>();
//        orderItems.add(new OrderItems("iPad", 1, 13.45, 1299.99));
//        orderItems.add(new OrderItems("Monitor", 2, 22.38, 679.99));
//        orderItems.add(new OrderItems("NoteBook", 5, 2.38, 29));
//        Address billingAddress = new Address("2901 S King Dr", "1301", "Chicago", "Illinois", 60616);
//        Address shippingAddress = new Address("3001 S King Dr", "1802", "Chicago", "Illinois", 60616);
//        List<OrderPayment> orderPayments = new ArrayList<>();
//        orderPayments.add(new OrderPayment("Credit Card", ZonedDateTime.now(), UUID.randomUUID().toString(), billingAddress));
//        orderPayments.add(new OrderPayment("Paypal", ZonedDateTime.now(), UUID.randomUUID().toString(), billingAddress));
//        orderPayments.add(new OrderPayment("Credit Card", ZonedDateTime.now(), UUID.randomUUID().toString(), billingAddress));
//        OrderDelivery orderDelivery = new OrderDelivery("Home delivery", 2.99);
//
//        Order order = new Order(OrderStatus.ORDER_IN_PROGRESS, UUID.randomUUID().toString(), orderItems, orderPayments, shippingAddress, ZonedDateTime.now(), ZonedDateTime.now(), orderDelivery);
//        this.orderList.add(order);
//
//        orderItems = new ArrayList<>();
//        orderItems.add(new OrderItems("Car Toy", 2, 13.45, 99.99));
//        orderItems.add(new OrderItems("Pen", 1, 1.15, 8.99));
//        orderPayments = new ArrayList<>();
//        orderPayments.add(new OrderPayment("Credit Card", ZonedDateTime.now(), UUID.randomUUID().toString(), billingAddress));
//        orderPayments.add(new OrderPayment("Paypal", ZonedDateTime.now(), UUID.randomUUID().toString(), billingAddress));
//        orderDelivery = new OrderDelivery("In-Store Pickup", 0);
//        order = new Order(OrderStatus.ORDER_IN_PROGRESS, UUID.randomUUID().toString(), orderItems, orderPayments, shippingAddress, ZonedDateTime.now(), ZonedDateTime.now(), orderDelivery);
//        this.orderList.add(order);
//
//    }

    public List<Order> getOrders() {
        Query query = entityManager.createQuery("SELECT ord FROM Order ord");
        return (List<Order>) query.getResultList();
    }

    public Order getOrderById(String id) {
        Query query = entityManager.createNativeQuery("SELECT * FROM Order WHERE id = :id");
        query.setParameter(id, id);
        List<Order> orders = query.getResultList();
        if (orders.size() == 0) return null;
        return orders.get(0);
    }

    public List<Order> getOrdersWithTimeInterval(String startTime, String endTime) {
        Query query = entityManager.createQuery("SELECT ord FROM Order ord " +
                "JOIN fetch ord.orderPayment JOIN fetch ord.orderItemsList " +
                "WHERE ord.dateCreated > :startTime AND ord.dateCreated < :endTime")
                .setParameter("startTime", startTime).setParameter("endTime", endTime);
        return (List<Order>) query.getResultList();
    }

    public Order saveOrder(Order order) {
        entityManager.persist(order);
        return order;
    }


    public Order cancelOrder(Order order) {
        Order cancelOrder = getOrderById(order.getId());
        cancelOrder.setStatus(OrderStatus.ORDER_CANCELLED);
        entityManager.merge(cancelOrder);
        return cancelOrder;
    }

    public Order updateOrder(Order order) {
        entityManager.merge(order);
        return order;
    }
}
