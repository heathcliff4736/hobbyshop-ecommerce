package com.ssg.noranekoshop.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name="members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false, unique = true)
    private String loginId;

    @Column(length = 100, nullable = false)
    private String loginPw;

    @Column(length = 20, nullable = true)
    private String phone;

    @Column(length = 200, nullable = true)
    private String address;

    // 회원 상태 (ACTIVE, INACTIVE, DELETED 등)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus status = MemberStatus.ACTIVE;

    @Column(nullable = false, unique = false)
    @CreationTimestamp
    private LocalDateTime created;

    @Column(nullable = true)
    private LocalDateTime deleted; // 탈퇴 시간

    public Member() {}

    public Member(String name, String loginId, String loginPw, String phone, String address) {
        this.name = name;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.phone = phone;
        this.address = address;
    }

    public void withdraw() {
        this.status = MemberStatus.DELETED;
        this.deleted = LocalDateTime.now();
    }
}
