package io.github.ladium1.study.practicesimplemsa.order.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "주문 상태 코드")
public enum OrderStatus {

    @Schema(description = "주문 생성")
    CREATED,
    @Schema(description = "주문 완료")
    COMPLETED,
    @Schema(description = "주문 취소")
    CANCELED

}
