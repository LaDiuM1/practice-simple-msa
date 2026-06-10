package io.github.ladium1.study.practicesimplemsa.member.application.service;

import io.github.ladium1.study.practicesimplemsa.member.application.dto.MemberCreateCommand;
import io.github.ladium1.study.practicesimplemsa.member.application.dto.MemberInfo;
import io.github.ladium1.study.practicesimplemsa.member.application.usecase.MemberUseCase;
import io.github.ladium1.study.practicesimplemsa.member.domain.model.Member;
import io.github.ladium1.study.practicesimplemsa.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements MemberUseCase {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public MemberInfo signUp(MemberCreateCommand command) {
        String encodedPassword = passwordEncoder.encode(command.password());
        memberRepository.validateUniqueByEmail(command.email());
        memberRepository.validateUniqueByPhoneNumber(command.phoneNumber());

        Member newMember = new Member(
                command.email(),
                encodedPassword,
                command.name(),
                command.phoneNumber(),
                command.address()
        );

        Member savedMember = memberRepository.save(newMember);
        return MemberInfo.from(savedMember);
    }

}
