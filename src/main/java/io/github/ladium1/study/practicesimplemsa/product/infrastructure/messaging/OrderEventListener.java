package io.github.ladium1.study.practicesimplemsa.product.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ladium1.study.practicesimplemsa.product.application.usecase.ProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private static final String ORDER_CREATED_TOPIC = "order.created";
    private static final String ORDER_CANCELED_TOPIC = "order.canceled";

    private final ProductUseCase productUseCase;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = ORDER_CREATED_TOPIC)
    public void handleOrderCreated(String message) {
        OrderStockMessage event = toMessage(message);
        productUseCase.decreaseStock(event.productId(), event.quantity());
    }

    @KafkaListener(topics = ORDER_CANCELED_TOPIC)
    public void handleOrderCanceled(String message) {
        OrderStockMessage event = toMessage(message);
        productUseCase.restoreStock(event.productId(), event.quantity());
    }

    private OrderStockMessage toMessage(String message) {
        try {
            return objectMapper.readValue(message, OrderStockMessage.class);
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException("주문 이벤트 역직렬화에 실패했습니다.", exception);
        }
    }
}
