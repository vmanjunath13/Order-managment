package com.egen.service;

import com.egen.model.dto.AddressDto;
import com.egen.model.dto.OrderDto;
import com.egen.model.dto.OrderPaymentDto;
import com.egen.model.entity.Address;
import com.egen.model.entity.Order;
import com.egen.model.entity.OrderItem;
import com.egen.model.entity.OrderPayment;
import com.egen.model.enums.DeliveryMethod;
import com.egen.model.enums.OrderStatus;
import com.egen.model.enums.PaymentMethod;
import com.egen.repository.OrderRepository;
import com.egen.service.implementation.OrderItemServiceImplementation;
import com.egen.service.implementation.OrderServiceImplementation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    OrderRepository ordersRepo;
    AddressService addressService;
    CustomerService customerService;
    OrderItemService itemService;
    OrderPaymentService paymentService;
    OrderService ordersService;

    @Before
    public void init_mocks() {
        itemService = Mockito.mock(OrderItemServiceImplementation.class);
        addressService = Mockito.mock(AddressService.class);
        ordersRepo = Mockito.mock(OrderRepository.class);
        paymentService = Mockito.mock(OrderPaymentService.class);
        customerService = Mockito.mock(CustomerService.class);
        ordersService = new OrderServiceImplementation(ordersRepo,itemService,customerService,addressService,paymentService);
    }

    @Test
    public void testCreateOrder(){

        Boolean create = this.ordersService.placeOrder(createOrderDto());
        Assertions.assertTrue(ordersService.placeOrder((createOrderDto())));
        Assertions.assertTrue(create);
    }
    
    @Test
    public void testCreateOrderById(){
        doReturn(createOrderData()).when(ordersService.getOrderById("8L"));
        Assert.assertEquals("ID dint match", java.util.Optional.ofNullable(createOrderData().getOrderId()),8L);
    }

    public Order createOrderData(){
        OrderItem items = new OrderItem();
        items.setItemId("8L");
        items.setItemQty(3);
        items.setItemName("iPhone");
        items.setTotal(1750.5);
        Set<OrderItem> itemSet = new HashSet<>();
        itemSet.add(items);

        List<OrderPayment> paymentSet = new ArrayList<>();

        Address address = new Address();
        address.setAddressId(UUID.randomUUID().toString());
        address.setAddressLine1("2901 S King Dr");
        address.setAddressLine2("1303");
        address.setCity("Chicago");
        address.setState("IL");
        address.setPostalCode("60616");
        OrderPayment payments = new OrderPayment();
        payments.setPaymentId(UUID.randomUUID().toString());
        payments.setPaymentMethod(PaymentMethod.CREDIT);
        payments.setPaymentDate(Timestamp.valueOf(LocalDate.now().toString()));
        payments.setPaymentConfirmationNumber(UUID.randomUUID().toString());
        payments.setBillingAddress(address);
        paymentSet.add(payments);
        Order order = new Order();
        order.setOrderId("8L");
        order.setShippingAddress(address);
        order.setOrderTotal(1700);
        order.setOrderPayment(paymentSet);
        order.setStatus(OrderStatus.ORDER_PLACED);
        order.setDateCreated(Timestamp.valueOf(LocalDate.now().toString()));
        return order;
    }
    public OrderDto createOrderDto(){
        OrderItem items = new OrderItem();
        items.setItemId("8L");
        items.setItemQty(3);
        items.setItemName("iPhone");
        items.setTotal(1750.5);
        Set<OrderItem> itemSet = new HashSet<>();
        itemSet.add(items);

        List<OrderPaymentDto> paymentSet = new ArrayList<>();

        AddressDto addressDto = new AddressDto();
        addressDto.setAddressId(UUID.randomUUID().toString());
        addressDto.setAddressLine1("2901 S King Dr");
        addressDto.setAddressLine2("1303");
        addressDto.setCity("Chicago");
        addressDto.setState("IL");
        addressDto.setPostalCode("60616");

        Address address = new Address();
        address.setAddressId(UUID.randomUUID().toString());
        address.setAddressLine1("2901 S King Dr");
        address.setAddressLine2("1303");
        address.setCity("Chicago");
        address.setState("IL");
        address.setPostalCode("60616");

        OrderPaymentDto payments = new OrderPaymentDto();
        payments.setPaymentId(UUID.randomUUID().toString());
        payments.setPaymentMethod(PaymentMethod.CREDIT);
        payments.setPaymentConfirmationNumber(UUID.randomUUID().toString());
        paymentSet.add(payments);
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId("8L");
        orderDto.setShippingAddress(addressDto);
        orderDto.setOrderTotal(1700);
        orderDto.setOrderPayment(paymentSet);
        orderDto.setStatus(OrderStatus.ORDER_PLACED);
        orderDto.setDateCreated(Timestamp.valueOf(LocalDate.now().toString()));
        return orderDto;
    }
}