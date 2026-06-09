package io.github.ladium1.study.practicesimplemsa.product.domain.exception;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(UUID productId) {
        super("상품을 찾을 수 없습니다. ID: " + productId);
    }

}
