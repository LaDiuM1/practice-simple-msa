package io.github.ladium1.study.practicesimplemsa.ai.application.service;

import io.github.ladium1.study.practicesimplemsa.ai.application.client.AiImageClient;
import io.github.ladium1.study.practicesimplemsa.ai.application.dto.AiImageAnalyzeCommand;
import io.github.ladium1.study.practicesimplemsa.ai.application.dto.AiImageAnalyzeInfo;
import io.github.ladium1.study.practicesimplemsa.ai.application.dto.AiImageGenerateCommand;
import io.github.ladium1.study.practicesimplemsa.ai.application.dto.AiImageGenerateInfo;
import io.github.ladium1.study.practicesimplemsa.ai.application.usecase.AiImageUseCase;
import io.github.ladium1.study.practicesimplemsa.ai.domain.exception.InvalidAiImageException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiImageService implements AiImageUseCase {

    private static final String DEFAULT_ANALYZE_PROMPT = "이 이미지를 설명해줘";
    private static final String DEFAULT_IMAGE_SIZE = "1024x1024";

    private final AiImageClient aiImageClient;

    @Override
    public AiImageAnalyzeInfo analyze(AiImageAnalyzeCommand command) {
        if (command.image() == null || command.image().length == 0) {
            throw new InvalidAiImageException();
        }

        String prompt = defaultIfBlank(command.prompt(), DEFAULT_ANALYZE_PROMPT);
        String answer = aiImageClient.analyzeImage(command.image(), command.contentType(), prompt);
        return AiImageAnalyzeInfo.of(prompt, answer);
    }

    @Override
    public AiImageGenerateInfo generate(AiImageGenerateCommand command) {
        String prompt = command.prompt().trim();
        String size = defaultIfBlank(command.size(), DEFAULT_IMAGE_SIZE);
        AiImageClient.GeneratedImage generatedImage = aiImageClient.generateImage(prompt, size);
        return AiImageGenerateInfo.of(prompt, generatedImage.format(), generatedImage.base64());
    }

    private String defaultIfBlank(String value, String defaultValue) {
        return (value == null || value.isBlank()) ? defaultValue : value.trim();
    }
}
