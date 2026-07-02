package io.github.ladium1.study.practicesimplemsa.ai.application.usecase;

import io.github.ladium1.study.practicesimplemsa.ai.application.dto.AiImageAnalyzeCommand;
import io.github.ladium1.study.practicesimplemsa.ai.application.dto.AiImageAnalyzeInfo;
import io.github.ladium1.study.practicesimplemsa.ai.application.dto.AiImageGenerateCommand;
import io.github.ladium1.study.practicesimplemsa.ai.application.dto.AiImageGenerateInfo;

public interface AiImageUseCase {

    AiImageAnalyzeInfo analyze(AiImageAnalyzeCommand command);

    AiImageGenerateInfo generate(AiImageGenerateCommand command);
}
