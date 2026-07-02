package io.github.ladium1.study.practicesimplemsa.ai.application.dto;

public record AiImageAnalyzeCommand(
        byte[] image,
        String contentType,
        String prompt
) {
}
