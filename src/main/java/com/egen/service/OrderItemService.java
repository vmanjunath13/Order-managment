package com.egen.service;

import com.egen.model.dto.OrderItemDto;
import com.egen.model.entity.Order;
import com.egen.model.entity.OrderItem;

public interface OrderItemService {

    boolean createItem(OrderItemDto orderItemDto);
    OrderItem getItem(String orderId);
    void updateItem(String orderId, int quantity);
    OrderItem updateOrderInItem(String orderId, Order order);

}
