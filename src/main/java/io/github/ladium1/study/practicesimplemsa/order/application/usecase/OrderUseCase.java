package io.github.ladium1.study.practicesimplemsa.order.application.usecase;

import io.github.ladium1.study.practicesimplemsa.order.application.dto.OrderCreateCommand;
import io.github.ladium1.study.practicesimplemsa.order.application.dto.OrderInfo;

import java.util.UUID;

public interface OrderUseCase {

    OrderInfo create(OrderCreateCommand command);

    OrderInfo get(UUID orderId);

    OrderInfo cancel(UUID orderId);
}
