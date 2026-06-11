package io.github.ladium1.study.practicesimplemsa.product.domain.exception;

import java.util.UUID;

public class ProductOutOfStockException extends RuntimeException {

    public ProductOutOfStockException(UUID productId) {
        super("상품 재고가 부족합니다. ID: " + productId);
    }

}
