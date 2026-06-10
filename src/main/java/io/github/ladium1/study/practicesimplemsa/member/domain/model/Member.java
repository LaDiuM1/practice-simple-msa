package io.github.ladium1.study.practicesimplemsa.member.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "\"member\"", schema = "study_msa", comment = "사용자 정보")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @Column(comment = "사용자 id")
    private UUID id;
    @Column(comment = "이메일")
    private String email;
    @Column(comment = "비밀번호")
    private String password;
    @Column(comment = "사용자명")
    private String name;
    @Column(comment = "사용자 상태")
    @Enumerated(EnumType.STRING)
    private MemberStatus status;
    @Column(comment = "휴대번호")
    private String phoneNumber;
    @Column(comment = "주소")
    private String address;
    @Column(comment = "생성일시")
    private LocalDateTime createdAt;
    @Column(comment = "수정일시")
    private LocalDateTime updatedAt;

    public Member(String email, String password, String name, String phoneNumber, String address) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    @PrePersist
    private void onCreate() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.status == null) {
            this.status = MemberStatus.ACTIVE;
        }
    }

    @PreUpdate
    private void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
