package io.github.ladium1.study.practicesimplemsa.order.infrastructure.messaging;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import io.github.ladium1.study.practicesimplemsa.order.application.event.OrderCanceledEvent;
import io.github.ladium1.study.practicesimplemsa.order.application.event.OrderCreatedEvent;
import io.github.ladium1.study.practicesimplemsa.order.application.event.OrderEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventPublisherAdaptor implements OrderEventPublisher {

    public static final String ORDER_CREATED_TOPIC = "order.created";
    public static final String ORDER_CANCELED_TOPIC = "order.canceled";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void publishCreated(OrderCreatedEvent event) {
        kafkaTemplate.send(ORDER_CREATED_TOPIC, event.orderId().toString(), toJson(event));
    }

    @Override
    public void publishCanceled(OrderCanceledEvent event) {
        kafkaTemplate.send(ORDER_CANCELED_TOPIC, event.orderId().toString(), toJson(event));
    }

    private String toJson(Object event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JacksonException exception) {
            throw new IllegalStateException("주문 이벤트 직렬화에 실패했습니다.", exception);
        }
    }
}
