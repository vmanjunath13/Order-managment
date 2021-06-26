package com.egen.service.kafka;

import com.egen.model.dto.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
public class OrderProducerServiceImpl {
    private final KafkaTemplate<String, OrderDto> kafkaTemplate;

    private final KafkaTemplate<String, OrderDto> customerKafkaTemplate;

    @Value("${kafka.topic.order.name}")
    private String STRING_TOPIC;

    @Value("${kafka.topic.order.name}")
    private String JSON_TOPIC;

    public OrderProducerServiceImpl(KafkaTemplate<String, OrderDto> kafkaTemplate, KafkaTemplate<String, OrderDto> customerKafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.customerKafkaTemplate = customerKafkaTemplate;
    }

    public boolean sendOrder(OrderDto OrderDto) {
        log.info(String.format("Producing message: %s", OrderDto));

        customerKafkaTemplate.executeInTransaction(t -> {
            ListenableFuture<SendResult<String, OrderDto>> future = t.send(JSON_TOPIC, OrderDto);
            future.addCallback(new ListenableFutureCallback<SendResult<String, OrderDto>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    log.info("Unable to process messsage due to "+ throwable.getMessage());
                }

                @Override
                public void onSuccess(SendResult<String, OrderDto> result) {
                    RecordMetadata sentOrder = result.getRecordMetadata();
                    log.info(String.format("=>{}", sentOrder.offset(), "{}", sentOrder.topic()));
                }
            });
            return true;
        });
        return true;
    }
}
