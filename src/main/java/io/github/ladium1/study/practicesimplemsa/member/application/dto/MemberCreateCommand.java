package io.github.ladium1.study.practicesimplemsa.member.application.dto;

public record MemberCreateCommand(
        String email,
        String password,
        String name,
        String phoneNumber,
        String address
) {
}
