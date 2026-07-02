package io.github.ladium1.study.practicesimplemsa.ai.application.dto;

import lombok.Builder;

@Builder
public record AiImageAnalyzeInfo(
        String prompt,
        String answer
) {
    public static AiImageAnalyzeInfo of(String prompt, String answer) {
        return AiImageAnalyzeInfo.builder()
                .prompt(prompt)
                .answer(answer)
                .build();
    }
}
