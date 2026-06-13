package io.github.ladium1.study.practicesimplemsa.order.domain.exception;

import java.util.UUID;

public class OrderProductNotFoundException extends RuntimeException {

    public OrderProductNotFoundException(UUID productId) {
        super("주문할 상품을 찾을 수 없습니다. ID: " + productId);
    }

}
