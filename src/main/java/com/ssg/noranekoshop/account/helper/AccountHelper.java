package com.ssg.noranekoshop.account.helper;

import com.ssg.noranekoshop.account.dto.AccountJoinRequest;
import com.ssg.noranekoshop.account.dto.AccountLoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AccountHelper {

    // 회원가입을 처리하는 메서드
    void join(AccountJoinRequest joinReq);

    // 로그인
    String login(AccountLoginRequest loginReq, HttpServletRequest request, HttpServletResponse response);

    // 회원 아이디 조회
    Integer getMemberId(HttpServletRequest request);

    // 로그인 여부 확인
    boolean isLoggedIn(HttpServletRequest request);

    // 로그아웃
    void logout(HttpServletRequest request, HttpServletResponse response);
}
