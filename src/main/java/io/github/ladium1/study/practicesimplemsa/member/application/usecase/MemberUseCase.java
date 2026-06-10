package io.github.ladium1.study.practicesimplemsa.member.application.usecase;

import io.github.ladium1.study.practicesimplemsa.member.application.dto.MemberCreateCommand;
import io.github.ladium1.study.practicesimplemsa.member.application.dto.MemberInfo;

public interface MemberUseCase {

    MemberInfo signUp(MemberCreateCommand command);

}
