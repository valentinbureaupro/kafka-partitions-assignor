package com.valentinbureaupro.kafka.partitions.assignor.infrastructure.consumer;

import com.valentinbureaupro.kafka.partitions.assignor.fixture.KafkaMessage;
import com.valentinbureaupro.kafka.partitions.assignor.fixture.KafkaMessage.KafkaMessageHeaders;
import com.valentinbureaupro.kafka.partitions.assignor.infrastructure.configuration.KafkaConsumerIntegrationTest;
import com.valentinbureaupro.kafka.partitions.assignor.infrastructure.consumer.event.UserEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

import static com.valentinbureaupro.kafka.partitions.assignor.infrastructure.consumer.fixture.UserEventFixture.newUserEvent;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@KafkaConsumerIntegrationTest
class UserKafkaConsumerIntegrationTest {

    private static final String USER_TOPIC = "user.cdc";

    @SpyBean
    private UserKafkaConsumer userKafkaConsumer;

    @Autowired
    private KafkaTemplate<String, UserEvent> kafkaTemplate;

    @Test
    void consume_givenUserEvent_shouldConsumeMessageFromKafka() {
        // Given
        final String correlationId = UUID.randomUUID().toString();
        final UserEvent userEvent = newUserEvent();

        // When
        kafkaTemplate.send(KafkaMessage.of(
                KafkaMessageHeaders.of(USER_TOPIC, userEvent.key(), correlationId),
                userEvent
        ));
        kafkaTemplate.flush();

        // Then
        verify(userKafkaConsumer, timeout(2000)).consume(correlationId, userEvent);
    }
}
