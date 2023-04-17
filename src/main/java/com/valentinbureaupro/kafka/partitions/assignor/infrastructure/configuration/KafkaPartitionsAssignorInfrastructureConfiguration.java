package com.valentinbureaupro.kafka.partitions.assignor.infrastructure.configuration;

import com.valentinbureaupro.kafka.partitions.assignor.infrastructure.consumer.UserKafkaConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.support.converter.MessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@Configuration
public class KafkaPartitionsAssignorInfrastructureConfiguration {

    @Configuration
    @EnableKafka
    static class KafkaPartitionsAssignorInfrastructureCommonKafkaConfiguration {

        @Bean
        MessageConverter messageConverter() {
            return new StringJsonMessageConverter();
        }
    }

    @Configuration
    static class KafkaPartitionsAssignorInfrastructureConsumerKafkaConfiguration {

        @Bean
        UserKafkaConsumer userKafkaConsumer() {
            return new UserKafkaConsumer();
        }
    }
}
