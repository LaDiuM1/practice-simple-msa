package io.github.ladium1.study.practicesimplemsa.ai.application.dto;

public record AiImageGenerateCommand(
        String prompt,
        String size
) {
}
