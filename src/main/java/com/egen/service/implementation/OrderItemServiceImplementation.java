package com.egen.service.implementation;

import com.egen.exception.OrderItemServiceException;
import com.egen.model.dto.OrderItemDto;
import com.egen.model.entity.Order;
import com.egen.model.entity.OrderItem;
import com.egen.repository.OrderItemRepository;
import com.egen.service.OrderItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrderItemServiceImplementation implements OrderItemService {

    final OrderItemRepository orderItemRepository;

    public OrderItemServiceImplementation(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public boolean createItem(OrderItemDto orderItemDto) {
        try {
            orderItemRepository.save(new OrderItem(orderItemDto.getItemName(), orderItemDto.getItemQty(), orderItemDto.getSubtotal(), orderItemDto.getTax(), orderItemDto.getTotal()));
            return true;
        } catch (Exception e) {
            throw new OrderItemServiceException("Order Item is not added to inventory, try again!");
        }
    }

    @Transactional(readOnly = true)
    public OrderItem getItem(String orderId) {
        try {
            Optional<OrderItem> item = orderItemRepository.findById(orderId);
            if(!item.isPresent())
                throw new Exception();
            else
                return item.get();
        }catch (Exception ex){
            throw new OrderItemServiceException("Sorry!! The item you are looking for is currently out of stock");
        }
    }

    @Transactional
    public void updateItem(String orderId, int quantity) {
        try {
            Optional<OrderItem> updatedItem = orderItemRepository.findById(orderId);
            if(updatedItem.isPresent()){
                updatedItem.get().setItemQty(quantity);
            }else
                throw new Exception();
            orderItemRepository.save(updatedItem.get());
        } catch (Exception ex) {
            throw new OrderItemServiceException("Failed to update the order");
        }
    }

    @Transactional
    public OrderItem updateOrderInItem(String orderId, Order order) {
        try {
            Optional<OrderItem> updatedItem = orderItemRepository.findById(orderId);
            if(updatedItem.isPresent()){
                updatedItem.get().setOrders(order);
                return orderItemRepository.save(updatedItem.get());
            }else
                throw new Exception();
        }catch (Exception ex){
            throw new OrderItemServiceException("Unable to place the order");
        }
    }
}
