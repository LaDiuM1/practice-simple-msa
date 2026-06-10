package io.github.ladium1.study.practicesimplemsa.member.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원 상태 상태 코드")
public enum MemberStatus {

    @Schema(description = "활동")
    ACTIVE,
    @Schema(description = "휴면")
    DORMANT,
    @Schema(description = "탈퇴")
    DELETED

}