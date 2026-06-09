package io.github.ladium1.study.practicesimplemsa.product.presentation.dto;

import io.github.ladium1.study.practicesimplemsa.product.application.dto.ProductCreateCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;

@Schema(description = "상품 생성 요청")
public record ProductCreateRequest(
        @Schema(description = "상품명", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "상품명을 입력해주세요.")
        String name,
        @Schema(description = "상품 가격", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "가격을 입력해주세요.")
        @PositiveOrZero(message = "가격은 0보다 작을 수 없습니다.")
        Long price,
        @Schema(description = "판매자 id")
        UUID sellerId
) {
    public ProductCreateCommand toCommand() {
        return new ProductCreateCommand(
                name,
                price,
                sellerId
        );
    }
}
