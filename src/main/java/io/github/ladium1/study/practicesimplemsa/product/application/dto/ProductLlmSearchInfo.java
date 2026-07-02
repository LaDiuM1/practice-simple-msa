package io.github.ladium1.study.practicesimplemsa.product.application.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ProductLlmSearchInfo(
        String question,
        String answer,
        List<ProductInfo> products
) {
    public static ProductLlmSearchInfo of(String question, String answer, List<ProductInfo> products) {
        return ProductLlmSearchInfo.builder()
                .question(question)
                .answer(answer)
                .products(products)
                .build();
    }
}
