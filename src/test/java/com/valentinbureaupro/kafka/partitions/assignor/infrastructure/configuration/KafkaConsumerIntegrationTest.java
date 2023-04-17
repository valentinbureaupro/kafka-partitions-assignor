package com.valentinbureaupro.kafka.partitions.assignor.infrastructure.configuration;

import com.valentinbureaupro.kafka.partitions.assignor.infrastructure.configuration.KafkaPartitionsAssignorInfrastructureConfiguration.KafkaPartitionsAssignorInfrastructureCommonKafkaConfiguration;
import com.valentinbureaupro.kafka.partitions.assignor.infrastructure.configuration.KafkaPartitionsAssignorInfrastructureConfiguration.KafkaPartitionsAssignorInfrastructureConsumerKafkaConfiguration;
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
                TestContainersConfiguration.class,
                KafkaPartitionsAssignorInfrastructureCommonKafkaConfiguration.class,
                KafkaPartitionsAssignorInfrastructureConsumerKafkaConfiguration.class
        }
)
public @interface KafkaConsumerIntegrationTest {
}
