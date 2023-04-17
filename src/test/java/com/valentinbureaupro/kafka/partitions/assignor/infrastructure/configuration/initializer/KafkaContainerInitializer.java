package com.valentinbureaupro.kafka.partitions.assignor.infrastructure.configuration.initializer;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

public class KafkaContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final String KAFKA_DOCKER_IMAGE_NAME = "confluentinc/cp-kafka";
    private static final String KAFKA_DOCKER_IMAGE_VERSION = "7.3.3";
    private static final String KAFKA_DOCKER_IMAGE = String.join(":", KAFKA_DOCKER_IMAGE_NAME, KAFKA_DOCKER_IMAGE_VERSION);

    private static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse(KAFKA_DOCKER_IMAGE));

    static {
        Startables.deepStart(
                KAFKA_CONTAINER
        ).join();
    }

    @Override
    public void initialize(final ConfigurableApplicationContext context) {
        TestPropertyValues.of(
                "spring.kafka.bootstrap-servers=" + KAFKA_CONTAINER.getBootstrapServers()
        ).applyTo(context.getEnvironment());
    }
}
