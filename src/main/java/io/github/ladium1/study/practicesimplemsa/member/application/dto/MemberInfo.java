package io.github.ladium1.study.practicesimplemsa.member.application.dto;

import io.github.ladium1.study.practicesimplemsa.member.domain.model.Member;
import io.github.ladium1.study.practicesimplemsa.member.domain.model.MemberStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record MemberInfo(
        UUID id,
        String email,
        String name,
        MemberStatus status,
        String phoneNumber,
        String address,
        LocalDateTime createdAt
) {
    public static MemberInfo from(Member member) {
        return MemberInfo.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .status(member.getStatus())
                .phoneNumber(member.getPhoneNumber())
                .address(member.getAddress())
                .build();
    }
}
