package io.github.ladium1.study.practicesimplemsa.order.infrastructure.messaging;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static io.github.ladium1.study.practicesimplemsa.order.infrastructure.messaging.OrderEventPublisherAdaptor.ORDER_CANCELED_TOPIC;
import static io.github.ladium1.study.practicesimplemsa.order.infrastructure.messaging.OrderEventPublisherAdaptor.ORDER_CREATED_TOPIC;

@Configuration
public class OrderTopicConfig {

    @Bean
    public NewTopic orderCreatedTopic() {
        return TopicBuilder.name(ORDER_CREATED_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic orderCanceledTopic() {
        return TopicBuilder.name(ORDER_CANCELED_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
