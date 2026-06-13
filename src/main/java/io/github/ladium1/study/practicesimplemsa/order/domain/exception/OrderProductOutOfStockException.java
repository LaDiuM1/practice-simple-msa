package io.github.ladium1.study.practicesimplemsa.order.domain.exception;

import java.util.UUID;

public class OrderProductOutOfStockException extends RuntimeException {

    public OrderProductOutOfStockException(UUID productId) {
        super("주문할 상품의 재고가 부족합니다. ID: " + productId);
    }

}
