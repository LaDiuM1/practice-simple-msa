package io.github.ladium1.study.practicesimplemsa.product.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Schema(description = "상품 LLM 검색 요청")
public record ProductLlmSearchRequest(
        @Schema(description = "검색 질문", example = "영상 편집용 노트북 추천해줘", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "질문을 입력해주세요.")
        String question,
        @Schema(description = "반환 상품 수", example = "3")
        @Positive(message = "반환 상품 수는 1 이상이어야 합니다.")
        Integer size
) {
    private static final int DEFAULT_SIZE = 3;

    public int sizeOrDefault() {
        return size == null ? DEFAULT_SIZE : size;
    }
}
