package io.github.ladium1.study.practicesimplemsa.ai.application.dto;

import lombok.Builder;

@Builder
public record AiImageGenerateInfo(
        String prompt,
        String format,
        String imageBase64
) {
    public static AiImageGenerateInfo of(String prompt, String format, String imageBase64) {
        return AiImageGenerateInfo.builder()
                .prompt(prompt)
                .format(format)
                .imageBase64(imageBase64)
                .build();
    }
}
