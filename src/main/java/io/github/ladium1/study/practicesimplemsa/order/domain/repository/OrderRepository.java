package io.github.ladium1.study.practicesimplemsa.order.domain.repository;

import io.github.ladium1.study.practicesimplemsa.order.domain.model.Order;

import java.util.UUID;

public interface OrderRepository {

    Order save(Order order);

    Order getById(UUID orderId);
}
