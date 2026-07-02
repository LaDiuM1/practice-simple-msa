package io.github.ladium1.study.practicesimplemsa.ai.domain.exception;

public class AiImageReadException extends RuntimeException {

    public AiImageReadException(Throwable cause) {
        super("이미지 파일을 읽는 데 실패했습니다.", cause);
    }

}
