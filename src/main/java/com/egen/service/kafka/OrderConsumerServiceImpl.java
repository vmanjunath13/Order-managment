package com.egen.service.kafka;

import com.egen.model.dto.OrderDto;
import com.egen.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderConsumerServiceImpl {
    public OrderService orderService;

    @Autowired
    public OrderConsumerServiceImpl(OrderService orderService){
        this.orderService = orderService;
    }

    @KafkaListener(containerFactory = "jsonKafkaListenerContainerFactory",
            topics = "${kafka.topic.order.name}",
            groupId = "${kafka.topic.order.groupId}")
    public void consumeCustomerData(OrderDto orderDto) {
        log.info("Consumed Message: {}, {}", orderDto.getOrderPayment(), orderDto.getOrderId());
        orderService.placeOrder(orderDto);
    }
}
