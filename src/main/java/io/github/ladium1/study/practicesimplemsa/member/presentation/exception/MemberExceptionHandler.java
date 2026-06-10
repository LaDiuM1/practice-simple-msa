package io.github.ladium1.study.practicesimplemsa.member.presentation.exception;

import io.github.ladium1.study.practicesimplemsa.member.domain.exception.MemberFieldDuplicateException;
import io.github.ladium1.study.practicesimplemsa.member.presentation.controller.MemberController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {MemberController.class})
public class MemberExceptionHandler {

    @ExceptionHandler(MemberFieldDuplicateException.class)
    public ProblemDetail handleMemberFieldDuplicate(MemberFieldDuplicateException exception) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );
    }

}
