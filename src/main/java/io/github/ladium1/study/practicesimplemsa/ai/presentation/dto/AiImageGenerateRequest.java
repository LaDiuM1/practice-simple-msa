package io.github.ladium1.study.practicesimplemsa.ai.presentation.dto;

import io.github.ladium1.study.practicesimplemsa.ai.application.dto.AiImageGenerateCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "AI 이미지 생성 요청")
public record AiImageGenerateRequest(
        @Schema(description = "이미지 생성 프롬프트", example = "노을 지는 바다를 바라보는 흰 고양이", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "프롬프트를 입력해주세요.")
        String prompt,
        @Schema(description = "이미지 크기", example = "1024x1024")
        String size
) {
    public AiImageGenerateCommand toCommand() {
        return new AiImageGenerateCommand(
                prompt,
                size
        );
    }
}
