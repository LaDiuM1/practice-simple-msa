package io.github.ladium1.study.practicesimplemsa.order.application.client;

import java.util.UUID;

public record ProductSnapshot(
        UUID id,
        String name,
        Long price
) {
}
