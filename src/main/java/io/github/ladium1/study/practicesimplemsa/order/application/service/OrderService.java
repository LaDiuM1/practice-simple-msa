package io.github.ladium1.study.practicesimplemsa.order.application.service;

import io.github.ladium1.study.practicesimplemsa.order.application.dto.OrderCreateCommand;
import io.github.ladium1.study.practicesimplemsa.order.application.dto.OrderInfo;
import io.github.ladium1.study.practicesimplemsa.order.application.usecase.OrderUseCase;
import io.github.ladium1.study.practicesimplemsa.order.domain.model.Order;
import io.github.ladium1.study.practicesimplemsa.order.domain.repository.OrderRepository;
import io.github.ladium1.study.practicesimplemsa.product.application.dto.ProductInfo;
import io.github.ladium1.study.practicesimplemsa.product.application.usecase.ProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService implements OrderUseCase {

    private final OrderRepository orderRepository;
    private final ProductUseCase productUseCase;

    @Override
    public OrderInfo get(UUID orderId) {
        Order order = orderRepository.getById(orderId);
        return OrderInfo.from(order);
    }

    @Override
    @Transactional
    public OrderInfo create(OrderCreateCommand command) {
        ProductInfo product = productUseCase.get(command.productId());
        productUseCase.decreaseStock(command.productId(), command.quantity());

        Order newOrder = new Order(
                command.productId(),
                command.buyerId(),
                command.quantity(),
                product.price() * command.quantity()
        );
        Order createdOrder = orderRepository.save(newOrder);
        return OrderInfo.from(createdOrder);
    }

    @Override
    @Transactional
    public OrderInfo cancel(UUID orderId) {
        Order order = orderRepository.getById(orderId);
        order.cancel();
        productUseCase.restoreStock(order.getProductId(), order.getQuantity());
        return OrderInfo.from(order);
    }
}
