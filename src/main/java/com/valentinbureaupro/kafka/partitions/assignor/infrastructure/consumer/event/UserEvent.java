package com.valentinbureaupro.kafka.partitions.assignor.infrastructure.consumer.event;

import java.util.UUID;

public record UserEvent (
        UUID id,
        String lastname,
        String firstname,
        String email
) {

    public String key() {
        return id.toString();
    }
}
