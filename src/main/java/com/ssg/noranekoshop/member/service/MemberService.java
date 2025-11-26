package com.ssg.noranekoshop.member.service;

import com.ssg.noranekoshop.member.entity.Member;
import org.springframework.transaction.annotation.Transactional;

public interface MemberService {

    // 회원 데이터를 저장하는 메서드, 회원의 이름, 로그인 아이디, 패스워드를 매개변수로 받는다
    void save(String name, String login_id, String login_pw, String phone, String address);

    // 회원데이터를 조회하는 메서드, 매개변수로 로그인아이디, 패스워드를 받고, 리턴타입으로 회원엔티티를 지정
    Member find(String login_id, String login_pw);

    //회원 탈퇴 기능 처리
    @Transactional
    void withdrawMember(Integer memberId);
}
