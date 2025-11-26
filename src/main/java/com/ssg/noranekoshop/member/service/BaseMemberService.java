package com.ssg.noranekoshop.member.service;

import com.ssg.noranekoshop.exception.DuplicateMemberException;
import com.ssg.noranekoshop.member.entity.Member;
import com.ssg.noranekoshop.member.entity.MemberStatus;
import com.ssg.noranekoshop.member.repository.MemberRepository;
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
    public void save(String name, String login_id, String login_pw, String phone, String address) {
        // 아이디 중복 체크
        if (memberRepository.existsByLoginId(login_id)) {
            throw new DuplicateMemberException("이미 사용 중인 이메일입니다.");
        }

        // 전화번호 중복 체크 (선택사항)
        if (phone != null && !phone.isEmpty() && memberRepository.existsByPhone(phone)) {
            throw new DuplicateMemberException("이미 등록된 전화번호입니다.");
        }

        memberRepository.save(new Member(name, login_id, login_pw, phone, address));
    }

    @Override
    public Member find(String login_id, String login_pw) {
        Optional<Member> anymember = memberRepository.findByLoginIdAndLoginPw(login_id, login_pw);
//        Optional<Member> member = memberRepository.findByLoginIdAndLoginPwAndStatus(
//                login_id,
//                login_pw,
//                MemberStatus.ACTIVE
//        );

        if (anymember.isEmpty()) {
            throw new RuntimeException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        Member member = anymember.get();

        if (member.getStatus() == MemberStatus.DELETED) {
            throw new RuntimeException("탈퇴한 회원입니다.");
        }

        if (member.getStatus() == MemberStatus.SUSPENDED) {
            throw new RuntimeException("정지된 회원입니다. 고객센터에 문의하세요.");
        }

//        return member.orElse(null);     // 회원 데이터가 있으면 해당 멤버를 리턴하고 없으면 NULL 리턴하여라
        return member;
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
