package io.github.ladium1.study.practicesimplemsa.product.presentation.dto;

import io.github.ladium1.study.practicesimplemsa.product.application.dto.ProductUpdateCommand;
import io.github.ladium1.study.practicesimplemsa.product.domain.model.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Schema(description = "상품 수정 요청")
public record ProductUpdateRequest(
        @Schema(description = "상품명")
        @Size(min = 1, message = "상품명을 입력해주세요.")
        String name,
        @Schema(description = "상품 가격")
        @PositiveOrZero(message = "가격은 0보다 작을 수 없습니다.")
        Long price,
        @Schema(description = "재고량")
        @PositiveOrZero(message = "재고는 0보다 작을 수 없습니다.")
        Integer stockQuantity,
        @Schema(description = "상품 상태")
        ProductStatus status,
        @Schema(description = "판매자 id")
        UUID sellerId
) {
    public ProductUpdateCommand toCommand(UUID productId) {
        return new ProductUpdateCommand(
                productId,
                name,
                price,
                stockQuantity,
                status
        );
    }
}
