package com.wuxp.study.mockito;


import com.wuxp.study.entities.Member;
import com.wuxp.study.services.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = {MockitoServiceMockTest.MockitoConfiguration.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MockitoServiceMockTest {

    @MockBean
    private MemberService memberService;

    @Autowired
    private MockitoService mockitoService;

    @Test
    public void testFindById() {
        Member result = new Member();
        result.setId(1L);
        given(this.memberService.findById(anyLong())).willReturn(result);
        Member member = mockitoService.findById(1L);
        Assertions.assertEquals(result, member);
    }

    @Configuration
    @Import({MockitoService.class, MemberService.class}) // A @Component injected with ExampleService
    static class MockitoConfiguration {
    }
}
