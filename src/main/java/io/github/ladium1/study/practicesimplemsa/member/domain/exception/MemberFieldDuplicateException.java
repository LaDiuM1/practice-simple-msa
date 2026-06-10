package io.github.ladium1.study.practicesimplemsa.member.domain.exception;

public class MemberFieldDuplicateException extends RuntimeException {

    public MemberFieldDuplicateException(String fieldName) {
        super("사용자의 다음 필드가 이미 존재합니다. : " + fieldName);
    }

}
