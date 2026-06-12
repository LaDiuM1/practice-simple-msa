package io.github.ladium1.study.practicesimplemsa.order.presentation.dto;

import io.github.ladium1.study.practicesimplemsa.order.application.dto.OrderCreateCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

@Schema(description = "주문 생성 요청")
public record OrderCreateRequest(
        @Schema(description = "상품 id", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "상품 id를 입력해주세요.")
        UUID productId,
        @Schema(description = "구매자 id", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "구매자 id를 입력해주세요.")
        UUID buyerId,
        @Schema(description = "주문 수량", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "주문 수량을 입력해주세요.")
        @Positive(message = "주문 수량은 1 이상이어야 합니다.")
        Integer quantity
) {
    public OrderCreateCommand toCommand() {
        return new OrderCreateCommand(
                productId,
                buyerId,
                quantity
        );
    }
}
