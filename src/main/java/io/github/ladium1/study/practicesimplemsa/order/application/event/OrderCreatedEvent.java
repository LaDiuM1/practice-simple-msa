package io.github.ladium1.study.practicesimplemsa.order.application.event;

import io.github.ladium1.study.practicesimplemsa.order.domain.model.Order;

import java.util.UUID;

public record OrderCreatedEvent(
        UUID orderId,
        UUID productId,
        Integer quantity
) {
    public static OrderCreatedEvent from(Order order) {
        return new OrderCreatedEvent(
                order.getId(),
                order.getProductId(),
                order.getQuantity()
        );
    }
}
