package io.github.ladium1.study.practicesimplemsa.order.infrastructure.persistence;

import io.github.ladium1.study.practicesimplemsa.order.domain.exception.OrderNotFoundException;
import io.github.ladium1.study.practicesimplemsa.order.domain.model.Order;
import io.github.ladium1.study.practicesimplemsa.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderRepositoryAdaptor implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Order save(Order order) {
        return orderJpaRepository.save(order);
    }

    @Override
    public Order getById(UUID orderId) {
        return orderJpaRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }
}
