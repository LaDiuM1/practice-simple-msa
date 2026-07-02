package io.github.ladium1.study.practicesimplemsa.ai.domain.exception;

public class InvalidAiImageException extends RuntimeException {

    public InvalidAiImageException() {
        super("분석할 이미지를 첨부해주세요.");
    }

}
