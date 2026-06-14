package io.github.ladium1.study.practicesimplemsa.order.application.client;

import java.util.UUID;

public interface ProductClient {

    ProductSnapshot getProduct(UUID productId);
}
