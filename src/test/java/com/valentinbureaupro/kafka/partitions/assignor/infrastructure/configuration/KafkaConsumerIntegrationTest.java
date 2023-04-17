package com.valentinbureaupro.kafka.partitions.assignor.infrastructure.configuration;

import com.valentinbureaupro.kafka.partitions.assignor.infrastructure.configuration.KafkaPartitionsAssignorInfrastructureConfiguration.KafkaPartitionsAssignorInfrastructureCommonKafkaConfiguration;
import com.valentinbureaupro.kafka.partitions.assignor.infrastructure.configuration.KafkaPartitionsAssignorInfrastructureConfiguration.KafkaPartitionsAssignorInfrastructureConsumerKafkaConfiguration;
import com.valentinbureaupro.kafka.partitions.assignor.infrastructure.configuration.initializer.KafkaContainerInitializer;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited

@IntegrationTest
@ContextConfiguration(
        classes = {
                KafkaPartitionsAssignorInfrastructureCommonKafkaConfiguration.class,
                KafkaPartitionsAssignorInfrastructureConsumerKafkaConfiguration.class
        },
        initializers = KafkaContainerInitializer.class
)
public @interface KafkaConsumerIntegrationTest {
}
