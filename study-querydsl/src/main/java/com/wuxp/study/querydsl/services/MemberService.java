package com.wuxp.study.querydsl.services;


import com.wuxp.study.querydsl.repositories.MemberRepository;
import com.wuxp.study.querydsl.entities.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;


    public Long addMember(Member member) {
//        if (member.getId()!=null){
//            throw new RuntimeException("n");
//        }

        Member result = memberRepository.save(member);

        return result.getId();
    }


    public Member findById(Long memberId) {
        Optional<Member> optional = memberRepository.findById(memberId);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }
}
