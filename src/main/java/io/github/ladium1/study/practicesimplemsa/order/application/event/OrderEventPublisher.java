package io.github.ladium1.study.practicesimplemsa.order.application.event;

public interface OrderEventPublisher {

    void publishCreated(OrderCreatedEvent event);

    void publishCanceled(OrderCanceledEvent event);
}
