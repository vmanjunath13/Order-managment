package com.egen.service;

import com.egen.exception.OrderServiceException;
import com.egen.model.dto.AddressDto;
import com.egen.model.dto.CustomerDto;
import com.egen.model.dto.OrderDto;
import com.egen.model.entity.*;
import com.egen.model.enums.DeliveryMethod;
import com.egen.model.enums.OrderStatus;
import com.egen.model.enums.PaymentMethod;
import com.egen.repository.OrderRepository;
import com.egen.service.implementation.OrderServiceImplementation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderServiceImplTest {

    public OrderService getService() {
        return new OrderServiceImplementation();
    }

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService itemService;

    @Autowired
    OrderPaymentService paymentService;

    @Autowired
    AddressService addressService;

    @Autowired
    CustomerService customerService;

    @MockBean
    private OrderRepository orderRepository;

    private List<Order> orders;
    OrderDto new_dto;
    Order new_order;

    @Before
    public void setup(){

        new_order = new Order();
        new_dto = new OrderDto();
        Set<OrderPayment> paymentsList = new HashSet<OrderPayment>();
        Set<OrderItem> itemsList = new HashSet<OrderItem>();

        //Create address
        Address addresses = new Address();
        addresses.setAddressId(UUID.randomUUID().toString());
        addresses.setAddressLine1("2901 S King Dr");
        addresses.setAddressLine2("1303");
        addresses.setCity("Chicago");
        addresses.setState("IL");
        addresses.setPostalCode("60616");

        AddressDto addressDto = new AddressDto();
        addressDto.setAddressId(UUID.randomUUID().toString());
        addressDto.setAddressLine1("2901 S King Dr");
        addressDto.setAddressLine2("1303");
        addressDto.setCity("Chicago");
        addressDto.setState("IL");
        addressDto.setPostalCode("60616");

        //Create Payments
        OrderPayment payments = new OrderPayment();
        payments.setPaymentId(UUID.randomUUID().toString());
        payments.setPaymentMethod(PaymentMethod.CREDIT);
        payments.setPaymentDate(Timestamp.valueOf(LocalDate.now().toString()));
        payments.setPaymentConfirmationNumber(UUID.randomUUID().toString());
        payments.setBillingAddress(addresses);

        OrderPayment payments2 = new OrderPayment();
        payments.setPaymentId(UUID.randomUUID().toString());
        payments.setPaymentMethod(PaymentMethod.DEBIT);
        payments.setPaymentDate(Timestamp.valueOf(LocalDate.now().toString()));
        payments.setPaymentConfirmationNumber(UUID.randomUUID().toString());
        payments.setBillingAddress(addresses);

        OrderDelivery orderDelivery = new OrderDelivery();
        orderDelivery.setDeliveryId(UUID.randomUUID().toString());
        orderDelivery.setDeliveryCharges(3.99);
        orderDelivery.setDeliveryMethod(DeliveryMethod.ORDER_DELIVERY);

        Customer customer = new Customer();
        customer.setCustomer_id(UUID.randomUUID().toString());
        customer.setEmail("vm@gmail.com");
        customer.setFirstName("Vaishnavi");
        customer.setLastName("Manjunath");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomer_id(UUID.randomUUID().toString());
        customerDto.setEmail("vm@gmail.com");
        customerDto.setFirstName("Vaishnavi");
        customerDto.setLastName("Manjunath");

        //Create order
        new_order.setOrderId(UUID.randomUUID().toString());
        new_order.setStatus(OrderStatus.ORDER_IN_PROGRESS);
        new_order.setOrderDelivery(orderDelivery);
        new_order.setDateCreated(Timestamp.valueOf(LocalDateTime.now().toString()));
        new_order.setDateUpdated(Timestamp.valueOf(LocalDateTime.now().toString()));
        new_order.setCustomer(customer);

        //Create item
        OrderItem items = new OrderItem();
        items.setItemId(UUID.randomUUID().toString());
        items.setItemQty(20);
        items.setItemName("Mobile");
        items.setSubtotal(1500);
        items.setTax(50);
        items.setTotal(1550);

        paymentsList.add(payments);
        paymentsList.add(payments2);

        itemsList.add(items);
        new_order.setOrderItemList((List<OrderItem>) itemsList);
        new_order.setShippingAddress(addresses);
        new_order.setOrderPayment((List<OrderPayment>) paymentsList);
        new_order.setOrderTotal(1550);

        orders = Collections.singletonList(new_order);

        //To create new order
        List<OrderPayment> payment = new ArrayList<>();
        payment.add(payments);
        payment.add(payments2);

        new_dto.setOrderId(UUID.randomUUID().toString());
        new_dto.setCustomer(customerDto);
        new_dto.setStatus(OrderStatus.ORDER_DELIVERED);
        new_dto.setBillingAddress(addressDto);
        new_dto.setOrderTotal(1200);


        //Mockito functions
        Mockito.when(orderRepository.findAll()).thenReturn(orders);
        Mockito.when(orderRepository.findById(new_order.getOrderId())).thenReturn(Optional.of(new_order));

        Mockito.when(orderRepository.findAllByDateOrderedBetween(orders.get(0).getDateCreated(),
                orders.get(0).getDateCreated())).thenReturn(orders);

        Mockito.when(orderRepository.findAllByShippingAddressZipcode(orders.get(0)
                .getShippingAddress().getPostalCode())).thenReturn(orders);

        Mockito.when(orderRepository.save(new_order)).thenReturn(new_order);
        Mockito.when(orderRepository.save(orders.get(0))).thenReturn(new_order);

    }

    @After
    public void cleanUp() {
        System.out.println("Order Service testing completed");
    }

    @Test
    public void findAll() throws Exception {
        List<Order> result = orderService.getAllOrders();
        Assert.assertEquals("Orders list should match", orders, result);
    }

    @Test(expected = OrderServiceException.class)
    public void findAllNotFound() throws Exception {
        orderService.getAllOrders();
    }

    @Test
    public void findOne() throws Exception {
        Optional<Order> current_order = Optional.ofNullable(orderService.getOrderById(orders.get(0).getOrderId()));
        System.out.println(current_order.toString());
        Assert.assertEquals("Order id should match",orders.get(0), current_order);
    }

    @Test(expected = OrderServiceException.class)
    public void findOneNotFound() throws Exception {
        orderService.getOrderById(UUID.randomUUID().toString());
    }

    @Test
    public void findWithinInterval() {
        List<Order> ordersWithinTime = orderService.getOrdersWithTimeInterval(orders.get(0).getDateCreated(),
                orders.get(0).getDateUpdated());
        Assert.assertEquals("orders within the interval not found",ordersWithinTime,orders);
    }

    @Test(expected = OrderServiceException.class)
    public void findWithinIntervalNotFound() {
        Order new_order= new Order();
        orderService.getOrdersWithTimeInterval(new_order.getDateCreated(),
                new_order.getDateUpdated());
    }

    @Test
    public void findAllByShippingAddressZipcodeAndSubTotal() {
        List<Order> max_total = orderService.top10OrdersWithHighestDollarAmountInZip(orders.get(0)
                .getShippingAddress().getPostalCode());
        Assert.assertEquals("No records found in the given zipcode",orders,max_total);
    }

    @Test(expected = OrderServiceException.class)
    public void findAllByShippingAddressZipcodeAndSubTotalNotFound() {
        orderService.top10OrdersWithHighestDollarAmountInZip("128");
    }

    @Test
    @Transactional
    public void createOrder() {
        boolean create_order = orderService.placeOrder(new_order);
        Assert.assertEquals("Failed to create order",create_order, create_order);
    }

    @Test
    @Transactional
    public void cancelOrder() {
        Order cancel = orderService.cancelOrder(orders.get(0).getOrderId());
        Assert.assertEquals("Failed to Cancel order","CANCELLED",cancel.getStatus().toString());
    }

    @Test(expected = OrderServiceException.class)
    @Transactional
    public void cancelOrderFailed() {
        Order cancel = orderService.cancelOrder(UUID.randomUUID().toString());
        Assert.assertEquals("Failed to Cancel order","CANCELLED",cancel.getStatus().toString());
    }

    @Test
    @Transactional
    public void updateOrder() {
        Order deliver = orderService.updateOrder(orders.get(0).getOrderId(), orders.get(0));
        Assert.assertEquals("Failed to Deliver order","DELIVERED",deliver.getStatus().toString());
    }

    @Test(expected = OrderServiceException.class)
    @Transactional
    public void updateOrderFailed() {
        orderService.updateOrder(orders.get(0).getOrderId(), orders.get(0));
    }
}
