package io.github.ladium1.study.practicesimplemsa.order.application.dto;

import io.github.ladium1.study.practicesimplemsa.order.domain.model.Order;
import io.github.ladium1.study.practicesimplemsa.order.domain.model.OrderStatus;
import lombok.Builder;

import java.util.UUID;

@Builder
public record OrderInfo(
        UUID id,
        UUID productId,
        UUID buyerId,
        Integer quantity,
        Long orderPrice,
        OrderStatus status
) {
    public static OrderInfo from(Order order) {
        return OrderInfo.builder()
                .id(order.getId())
                .productId(order.getProductId())
                .buyerId(order.getBuyerId())
                .quantity(order.getQuantity())
                .orderPrice(order.getOrderPrice())
                .status(order.getStatus())
                .build();
    }
}
