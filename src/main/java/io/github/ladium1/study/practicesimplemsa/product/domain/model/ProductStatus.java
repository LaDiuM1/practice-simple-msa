package io.github.ladium1.study.practicesimplemsa.product.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "상품 판매 상태 코드")
public enum ProductStatus {

    @Schema(description = "판매 대기")
    PREPARING,
    @Schema(description = "판매 중")
    FOR_SALE,
    @Schema(description = "품절")
    SOLD_OUT,
    @Schema(description = "판매 종료")
    DISCONTINUED

}