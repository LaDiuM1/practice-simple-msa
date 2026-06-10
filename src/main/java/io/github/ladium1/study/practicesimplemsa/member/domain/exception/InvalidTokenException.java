package io.github.ladium1.study.practicesimplemsa.member.domain.exception;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException() {
        super("유효하지 않은 토큰입니다.");
    }
}