package io.github.ladium1.study.practicesimplemsa.order.domain.exception;

import java.util.UUID;

public class OrderAlreadyCanceledException extends RuntimeException {

    public OrderAlreadyCanceledException(UUID orderId) {
        super("이미 취소된 주문입니다. ID: " + orderId);
    }

}
