package com.valentinbureaupro.kafka.partitions.assignor.infrastructure.consumer;

import com.valentinbureaupro.kafka.partitions.assignor.infrastructure.consumer.event.UserEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

@Slf4j
public class UserKafkaConsumer {

    @KafkaListener(topics = "${kafka-partitions-assignor.consumer.kafka.user.topic-name}")
    public void consume(
            @Header(KafkaHeaders.CORRELATION_ID) final String correlationId,
            @Payload final UserEvent userEvent
    ) {
        log.info("Consume UserEvent {} from User {}", correlationId, userEvent);
    }
}
