package com.valentinbureaupro.kafka.partitions.assignor.fixture;

import lombok.Value;
import lombok.experimental.UtilityClass;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.Map;

@Value(staticConstructor = "of")
public class KafkaMessage<T> implements Message<T> {

    MessageHeaders headers;
    T payload;

    @UtilityClass
    public static class KafkaMessageHeaders {

        public static MessageHeaders of(final String topic, final String key, final String correlationId) {
            return new MessageHeaders(Map.of(
                    KafkaHeaders.TOPIC, topic,
                    KafkaHeaders.KEY, key,
                    KafkaHeaders.CORRELATION_ID, correlationId
            ));
        }
    }
}
