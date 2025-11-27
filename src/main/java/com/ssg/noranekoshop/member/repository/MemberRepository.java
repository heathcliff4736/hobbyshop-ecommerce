package com.ssg.noranekoshop.member.repository;

import com.ssg.noranekoshop.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ssg.noranekoshop.member.entity.MemberStatus;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    // null 방지하기 위해 Optional 사용
    // id와 패스워드로 회원 정보를 조회
    Optional<Member> findByLoginIdAndLoginPw(String loginId, String loginPw);

    // 아이디로 회원 정보 조회
    Optional<Member> findByLoginId(String loginId);

    // 이메일 중복 검사
    boolean existsByLoginId(String loginId);

    // 전화번호 중복 검사
    boolean existsByPhone(String phone);
}
