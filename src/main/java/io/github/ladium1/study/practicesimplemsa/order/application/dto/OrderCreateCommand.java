package io.github.ladium1.study.practicesimplemsa.order.application.dto;

import java.util.UUID;

public record OrderCreateCommand(
        UUID productId,
        UUID buyerId,
        Integer quantity
) {
}
