package io.github.ladium1.study.practicesimplemsa.product.application.dto;

import io.github.ladium1.study.practicesimplemsa.product.domain.model.Product;
import io.github.ladium1.study.practicesimplemsa.product.domain.model.ProductStatus;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ProductInfo(
        UUID id,
        String name,
        ProductStatus status,
        Long price,
        UUID sellerId
) {
    public static ProductInfo from(Product product) {
        return ProductInfo
                .builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .status(product.getStatus())
                .sellerId(product.getSellerId())
                .build();
    }
}
