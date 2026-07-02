package io.github.ladium1.study.practicesimplemsa.ai.presentation.controller;

import io.github.ladium1.study.practicesimplemsa.ai.application.dto.AiImageAnalyzeCommand;
import io.github.ladium1.study.practicesimplemsa.ai.application.dto.AiImageAnalyzeInfo;
import io.github.ladium1.study.practicesimplemsa.ai.application.dto.AiImageGenerateInfo;
import io.github.ladium1.study.practicesimplemsa.ai.application.usecase.AiImageUseCase;
import io.github.ladium1.study.practicesimplemsa.ai.domain.exception.AiImageReadException;
import io.github.ladium1.study.practicesimplemsa.ai.presentation.dto.AiImageGenerateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/ai/images")
@RequiredArgsConstructor
public class AiImageController {

    private final AiImageUseCase aiImageUseCase;

    @Operation(summary = "이미지 분석", description = "업로드 이미지에 대한 AI 분석 결과 반환")
    @PostMapping(value = "/analyze", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AiImageAnalyzeInfo> analyze(
            @Parameter(description = "분석할 이미지") @RequestPart("image") MultipartFile image,
            @Parameter(description = "선택 프롬프트") @RequestParam(required = false) String prompt) {
        AiImageAnalyzeInfo analyzeInfo = aiImageUseCase.analyze(toAnalyzeCommand(image, prompt));
        return ResponseEntity.ok(analyzeInfo);
    }

    @Operation(summary = "이미지 생성", description = "프롬프트 기반 이미지 생성 후 Base64 반환")
    @PostMapping("/generate")
    public ResponseEntity<AiImageGenerateInfo> generate(@Valid @RequestBody AiImageGenerateRequest request) {
        AiImageGenerateInfo generateInfo = aiImageUseCase.generate(request.toCommand());
        return ResponseEntity.ok(generateInfo);
    }

    private AiImageAnalyzeCommand toAnalyzeCommand(MultipartFile image, String prompt) {
        try {
            return new AiImageAnalyzeCommand(image.getBytes(), image.getContentType(), prompt);
        } catch (IOException exception) {
            throw new AiImageReadException(exception);
        }
    }
}
