package io.github.ladium1.study.practicesimplemsa.order.application.service;

import io.github.ladium1.study.practicesimplemsa.order.application.client.ProductClient;
import io.github.ladium1.study.practicesimplemsa.order.application.client.ProductSnapshot;
import io.github.ladium1.study.practicesimplemsa.order.application.dto.OrderCreateCommand;
import io.github.ladium1.study.practicesimplemsa.order.application.dto.OrderInfo;
import io.github.ladium1.study.practicesimplemsa.order.application.event.OrderCanceledEvent;
import io.github.ladium1.study.practicesimplemsa.order.application.event.OrderCreatedEvent;
import io.github.ladium1.study.practicesimplemsa.order.application.event.OrderEventPublisher;
import io.github.ladium1.study.practicesimplemsa.order.application.usecase.OrderUseCase;
import io.github.ladium1.study.practicesimplemsa.order.domain.model.Order;
import io.github.ladium1.study.practicesimplemsa.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService implements OrderUseCase {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final OrderEventPublisher orderEventPublisher;

    @Override
    public OrderInfo get(UUID orderId) {
        Order order = orderRepository.getById(orderId);
        return OrderInfo.from(order);
    }

    @Override
    @Transactional
    public OrderInfo create(OrderCreateCommand command) {
        ProductSnapshot product = productClient.getProduct(command.productId());

        Order newOrder = new Order(
                command.productId(),
                command.buyerId(),
                command.quantity(),
                product.price() * command.quantity()
        );
        Order createdOrder = orderRepository.save(newOrder);
        orderEventPublisher.publishCreated(OrderCreatedEvent.from(createdOrder));
        return OrderInfo.from(createdOrder);
    }

    @Override
    @Transactional
    public OrderInfo cancel(UUID orderId) {
        Order order = orderRepository.getById(orderId);
        order.cancel();
        orderEventPublisher.publishCanceled(OrderCanceledEvent.from(order));
        return OrderInfo.from(order);
    }
}
