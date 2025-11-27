package com.ssg.noranekoshop.member.service;

import com.ssg.noranekoshop.exception.DuplicateMemberException;
import com.ssg.noranekoshop.member.entity.Member;
import com.ssg.noranekoshop.member.entity.MemberStatus;
import com.ssg.noranekoshop.member.repository.MemberRepository;
import com.ssg.noranekoshop.common.util.HashUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BaseMemberService implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void save(String name, String loginId, String loginPw, String phone, String address) {

        // 솔트 생성
        String loginPwSalt = HashUtils.generateSalt(16);

        // 입력 패스워드에 솔트를 적용
        String loginPwSalted = HashUtils.generateHash(loginPw, loginPwSalt);

        // 아이디 중복 체크
        if (memberRepository.existsByLoginId(loginId)) {
            throw new DuplicateMemberException("이미 사용 중인 이메일입니다.");
        }

        // 전화번호 중복 체크 (선택사항)
        if (phone != null && !phone.isEmpty() && memberRepository.existsByPhone(phone)) {
            throw new DuplicateMemberException("이미 등록된 전화번호입니다.");
        }

        memberRepository.save(new Member(name, loginId, loginPwSalted, loginPwSalt, phone, address));
    }

    @Override
    public Member find(String loginId, String loginPw) {
        Optional<Member> memberOptional = memberRepository.findByLoginId(loginId);

        // 회원 데이터가 있으면
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();

            if (member.getStatus() == MemberStatus.ACTIVE) {
                // 솔트 조회
                String loginPwSalt = memberOptional.get().getLoginPwSalt();

                // 입력 패스워드에 솔트를 적용
                String loginPwSalted = HashUtils.generateHash(loginPw, loginPwSalt);

                // 저장된 회원 로그인 패스워드와 일치한다면
                if (member.getLoginPw().equals(loginPwSalted)) {
                    return member;
                }
            } else if (member.getStatus() == MemberStatus.SUSPENDED) {
                throw new RuntimeException("정지된 회원입니다. 고객센터에 문의하세요.");
            } else {
                throw new RuntimeException("올바르지 않은 로그인 정보입니다..");
            }
        }
        return null;
    }


    //회원 탈퇴 기능 처리
    @Transactional
    @Override
    public void withdrawMember(Integer memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

        // 이미 탈퇴한 회원인지 확인
        if (member.getStatus() == MemberStatus.DELETED) {
            throw new RuntimeException("이미 탈퇴한 회원입니다.");
        }

        // 회원 탈퇴 처리 (실제로 삭제하지 않음)
        member.withdraw();
        memberRepository.save(member);
    }
}
