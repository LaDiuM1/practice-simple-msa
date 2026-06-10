package io.github.ladium1.study.practicesimplemsa.member.infrastructure.persistence;

import io.github.ladium1.study.practicesimplemsa.member.domain.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemberJpaRepository extends JpaRepository<Member, UUID> {

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

}
