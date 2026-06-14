package io.github.ladium1.study.practicesimplemsa.product.infrastructure.messaging;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OrderStockMessage(
        UUID orderId,
        UUID productId,
        Integer quantity
) {
}
