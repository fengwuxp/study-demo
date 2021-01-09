package com.wuxp.study.mockito;


import com.wuxp.study.entities.Member;
import com.wuxp.study.repositories.MemberRepository;
import com.wuxp.study.services.MemberService;
import com.wuxp.study.services.MemberServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;


@DataJpaTest
public class MockitoServiceSpyTest {

    /**
     * 来表示一个“间谍对象”，允许它的某些方法被模拟，而剩下的方法仍然是真实的方法。
     */
    @SpyBean
    private MemberServiceImpl memberService;


    @Test
    public void testFindById() {
        Member result = new Member();
        result.setId(1L);
        given(this.memberService.findById(anyLong())).willReturn(result);
        Member member = memberService.findById(1L);
        Assertions.assertEquals(result, member);
        Assertions.assertEquals(memberService.findNameById(1L), "name@1");
    }


}
