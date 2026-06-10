package io.github.ladium1.study.practicesimplemsa.member.domain.repository;

import io.github.ladium1.study.practicesimplemsa.member.domain.model.Member;

public interface MemberRepository {

    Member save(Member member);

    void validateUniqueByEmail(String email);

    void validateUniqueByPhoneNumber(String phoneNumber);


}
