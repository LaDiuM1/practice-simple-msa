package io.github.ladium1.study.practicesimplemsa.member.domain.exception;

public class MemberFieldDuplicateException extends RuntimeException {

    public MemberFieldDuplicateException(String fieldName) {
        super("다음 필드는 중복될 수 없습니다. 필드명: " + fieldName);
    }

}
