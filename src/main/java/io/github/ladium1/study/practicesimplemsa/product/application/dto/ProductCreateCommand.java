package io.github.ladium1.study.practicesimplemsa.product.application.dto;

import java.util.UUID;

public record ProductCreateCommand(
        String name,
        Long price,
        UUID sellerId
) {
}
