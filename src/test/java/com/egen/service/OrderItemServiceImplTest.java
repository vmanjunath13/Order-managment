package com.egen.service;


import com.egen.exception.OrderItemServiceException;
import com.egen.model.dto.OrderItemDto;
import com.egen.model.entity.Order;
import com.egen.model.entity.OrderItem;
import com.egen.repository.OrderItemRepository;
import com.egen.service.implementation.OrderItemServiceImplementation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderItemServiceImplTest {

    @Bean
    public OrderItemService getService() {
        return new OrderItemServiceImplementation();
    }

    @Autowired
    OrderItemService itemService;

    @MockBean
    OrderItemRepository itemRepository;

    OrderItem item;
    Order orders;
    OrderItemDto itemDto;

    @Before
    public void setUp(){
        item  = new OrderItem();
        itemDto = new OrderItemDto();
        itemDto.setItemId(UUID.randomUUID().toString());
        itemDto.setItemQty(20);
        itemDto.setItemName("Mobile");
        itemDto.setSubtotal(1500);
        itemDto.setTax(50);
        itemDto.setTotal(1550);

        item.setItemId(itemDto.getItemId());
        item.setItemQty(itemDto.getItemQty());
        item.setItemName(itemDto.getItemName());
        item.setSubtotal(itemDto.getSubtotal());
        item.setTax(itemDto.getTax());
        item.setTotal(itemDto.getTotal());
        orders = new Order();
        orders.setOrderId(UUID.randomUUID().toString());
        item.setOrders(orders);
        Mockito.when(itemRepository.save(item)).thenReturn(item);
        Mockito.when(itemRepository.findById(item.getItemId())).thenReturn(Optional.of(item));
    }

    @After
    public void cleanUp() {
        System.out.println("Order Item Service testing completed");
    }

    @Test
    public void createItem() {
        boolean new_item = itemService.createItem(itemDto);
        Assert.assertTrue("Failed to add item ", new_item);
    }

    @Test
    public void getItem() {
        OrderItem findItem = itemService.getItem(item.getItemId());
        Assert.assertEquals("No items found", item, findItem);
    }

    //Provide Id which doesnt exist
    @Test(expected = OrderItemServiceException.class)
    public void noItemPresent() {
        itemService.getItem(UUID.randomUUID().toString());
    }

    @Test
    public void updateItem() {
        int currentStock = item.getItemQty();
        itemService.updateItem(item.getItemId(),currentStock-3 );
        Assert.assertEquals("Failed to update order", Optional.of(currentStock - 3), item.getItemQty());
    }

    //Provide Id which doesnt exist
    @Test(expected = OrderItemServiceException.class)
    public void updateItemFailed() {
        int currentStock = item.getItemQty();
        itemService.updateItem(UUID.randomUUID().toString(),currentStock-3 );
    }
    @Test
    public void updateOrderIdInItem() {
        OrderItem new_item = itemService.updateOrderInItem(item.getItemId(), orders);
        Assert.assertEquals("Failed to update Order id", item, new_item);
    }

    //Provide Id which doesnt exist
    @Test(expected = OrderItemServiceException.class)
    public void updateOrderIdInItemFailed() {
        itemService.updateOrderInItem(UUID.randomUUID().toString(), orders);
    }
}
