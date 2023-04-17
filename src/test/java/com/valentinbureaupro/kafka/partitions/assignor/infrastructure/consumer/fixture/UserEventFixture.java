package com.valentinbureaupro.kafka.partitions.assignor.infrastructure.consumer.fixture;

import com.valentinbureaupro.kafka.partitions.assignor.infrastructure.consumer.event.UserEvent;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class UserEventFixture {

    public static UserEvent newUserEvent() {
        return new UserEvent(
                UUID.randomUUID(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
        );
    }
}
