package io.github.ladium1.study.practicesimplemsa.member.presentation.dto;

import io.github.ladium1.study.practicesimplemsa.member.application.dto.MemberCreateCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "회원 생성 요청")
public record MemberCreateRequest(
        @Schema(description = "이메일", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        String email,
        @Schema(description = "비밀번호", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password,
        @Schema(description = "사용자명", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "사용자명을 입력해주세요.")
        String name,
        @Schema(description = "휴대번호")
        String phoneNumber,
        @Schema(description = "주소")
        String address
) {
    public MemberCreateCommand toCommand() {
        return new MemberCreateCommand(
                email,
                password,
                name,
                phoneNumber,
                address
        );
    }
}