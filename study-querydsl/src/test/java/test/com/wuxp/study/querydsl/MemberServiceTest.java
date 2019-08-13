package test.com.wuxp.study.querydsl;

import com.wuxp.study.querydsl.WuxpStudyQueryDSLApplication;
import com.wuxp.study.querydsl.config.StudyConfig;
import com.wuxp.study.querydsl.entities.Member;
import com.wuxp.study.querydsl.services.MemberService;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import test.com.wuxp.study.TestServiceMain;

import java.util.Date;

/**
 * MemberService Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>二月 16, 2019</pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestServiceMain.class})
public class MemberServiceTest {


    @Autowired
    MemberService memberService;


    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: addMember(Member member)
     */
    @Test
    public void testAddMember() throws Exception {
        Member member = new Member();
        member.setBirthday(new Date())
                .setMobilePhone("128781823")
                .setName("张三")
                .setNickname("哈哈哈");
        Long memberId = memberService.addMember(member);
        System.out.println(memberId);
    }

    /**
     * Method: findById(Long memberId)
     */
    @Test
    public void testFindById() throws Exception {
        Member member = memberService.findById(1L);
        System.out.println(member);
    }


} 
