package com.wuxp.study.repositories;

import com.wuxp.study.entities.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void findById() {
        Optional<Member> member = memberRepository.findById(1L);
        Assertions.assertFalse(member.isPresent());
    }


}
