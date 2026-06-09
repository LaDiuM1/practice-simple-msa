package io.github.ladium1.study.practicesimplemsa.product.application.dto;

import io.github.ladium1.study.practicesimplemsa.product.domain.model.ProductStatus;

import java.util.UUID;

public record ProductUpdateCommand(
        UUID productId,
        String name,
        Long price,
        Integer stockQuantity,
        ProductStatus status
) {
}
