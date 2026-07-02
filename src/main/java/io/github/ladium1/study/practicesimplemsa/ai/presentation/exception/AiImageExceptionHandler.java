package io.github.ladium1.study.practicesimplemsa.ai.presentation.exception;

import io.github.ladium1.study.practicesimplemsa.ai.domain.exception.AiImageReadException;
import io.github.ladium1.study.practicesimplemsa.ai.domain.exception.InvalidAiImageException;
import io.github.ladium1.study.practicesimplemsa.ai.presentation.controller.AiImageController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {AiImageController.class})
public class AiImageExceptionHandler {

    @ExceptionHandler(InvalidAiImageException.class)
    public ProblemDetail handleInvalidAiImage(InvalidAiImageException exception) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );
    }

    @ExceptionHandler(AiImageReadException.class)
    public ProblemDetail handleAiImageRead(AiImageReadException exception) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );
    }

}
