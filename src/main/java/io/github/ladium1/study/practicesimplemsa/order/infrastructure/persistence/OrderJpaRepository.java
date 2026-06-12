package io.github.ladium1.study.practicesimplemsa.order.infrastructure.persistence;

import io.github.ladium1.study.practicesimplemsa.order.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<Order, UUID> {
}
