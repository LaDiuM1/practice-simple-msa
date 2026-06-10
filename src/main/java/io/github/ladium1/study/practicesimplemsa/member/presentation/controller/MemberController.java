package io.github.ladium1.study.practicesimplemsa.member.presentation.controller;

import io.github.ladium1.study.practicesimplemsa.member.application.dto.MemberInfo;
import io.github.ladium1.study.practicesimplemsa.member.application.usecase.MemberUseCase;
import io.github.ladium1.study.practicesimplemsa.member.presentation.dto.MemberCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberUseCase memberUseCase;

    @PostMapping
    @Operation(summary = "회원가입")
    public ResponseEntity<MemberInfo> signUp(@Valid @RequestBody MemberCreateRequest request) {
        MemberInfo memberInfo = memberUseCase.signUp(request.toCommand());
        return ResponseEntity.created(URI.create("/members/" + memberInfo.id())).body(memberInfo);
    }
}
