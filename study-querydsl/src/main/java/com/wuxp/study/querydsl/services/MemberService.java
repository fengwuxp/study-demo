package com.wuxp.study.querydsl.services;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.wuxp.study.querydsl.entities.QMember;
import com.wuxp.study.querydsl.repositories.MemberRepository;
import com.wuxp.study.querydsl.entities.Member;
import com.wuxp.study.querydsl.services.req.QueryMemberReq;
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
        return optional.orElse(null);
    }

    public Member find(QueryMemberReq req) {

        QMember qMember = QMember.member;
        Predicate predicate = qMember.id.eq(req.getId())
                .and(qMember.name.eq(req.getName()));

        BooleanBuilder booleanBuilder=new BooleanBuilder();

        return memberRepository.findOne(predicate).orElse(null);
    }
}
