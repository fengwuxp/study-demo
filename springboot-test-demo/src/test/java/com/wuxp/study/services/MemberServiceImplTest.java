package com.wuxp.study.services;

import com.wuxp.study.entities.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest(properties = {"spring.datasource.schema=classpath:jdbc-schema.sql"},
        excludeAutoConfiguration = {JacksonAutoConfiguration.class, HttpMessageConvertersAutoConfiguration.class})
@ActiveProfiles("test")
@ComponentScan(value = {"com.wuxp.study.services", "com.wuxp.study.repositories"})
class MemberServiceImplTest {

    @Autowired
    private MemberService memberService;

    @Test
    public void testFindById() {
        Member member = memberService.findById(1L);
        Assertions.assertNull(member);
    }
}