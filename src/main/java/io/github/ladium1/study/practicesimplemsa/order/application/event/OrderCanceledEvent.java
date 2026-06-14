package io.github.ladium1.study.practicesimplemsa.order.application.event;

import io.github.ladium1.study.practicesimplemsa.order.domain.model.Order;

import java.util.UUID;

public record OrderCanceledEvent(
        UUID orderId,
        UUID productId,
        Integer quantity
) {
    public static OrderCanceledEvent from(Order order) {
        return new OrderCanceledEvent(
                order.getId(),
                order.getProductId(),
                order.getQuantity()
        );
    }
}
