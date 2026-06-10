package io.github.ladium1.study.practicesimplemsa.member.infrastructure.persistence;

import io.github.ladium1.study.practicesimplemsa.member.domain.exception.MemberFieldDuplicateException;
import io.github.ladium1.study.practicesimplemsa.member.domain.model.Member;
import io.github.ladium1.study.practicesimplemsa.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberRepositoryAdaptor implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Member save(Member member) {
        return memberJpaRepository.save(member);
    }

    @Override
    public void validateUniqueByEmail(String email) {
        if(memberJpaRepository.existsByEmail(email)) {
            throw new MemberFieldDuplicateException("email");
        }
    }

    @Override
    public void validateUniqueByPhoneNumber(String phoneNumber) {
        if(memberJpaRepository.existsByPhoneNumber(phoneNumber)) {
            throw new MemberFieldDuplicateException("phoneNumber");
        }
    }
}
