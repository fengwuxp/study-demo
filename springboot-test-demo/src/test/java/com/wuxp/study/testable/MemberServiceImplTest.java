///*
//package com.wuxp.study.testable;
//
//import com.alibaba.testable.core.annotation.MockMethod;
//import com.alibaba.testable.core.annotation.MockWith;
//import com.alibaba.testable.core.model.MockDiagnose;
//import com.alibaba.testable.processor.annotation.EnablePrivateAccess;
//import com.wuxp.study.entities.Member;
//import com.wuxp.study.repositories.MemberRepository;
//import com.wuxp.study.services.MemberService;
//import com.wuxp.study.services.MemberServiceImpl;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.test.context.ActiveProfiles;
//
////@MockWith(diagnose = MockDiagnose.ENABLE)
////@DataJpaTest(properties = {"spring.jpa.hibernate.auto-ddl=update"})
////@ActiveProfiles("test")
////@ComponentScan(value = {"com.wuxp.study.services", "com.wuxp.study.repositories"})
////@EnablePrivateAccess
//class MemberServiceImplTest {
//
//    MemberServiceImpl memberService = new MemberServiceImpl();
////    @Autowired
////    private MemberService memberService;
//
//    // 定义Mock目标和替代方法
//    // 约定Mock方法比原方法多一个参数，传入调用者本身
//    // 因此是替换DatabaseDAO类的int write()方法调用
//    @MockMethod()
//    Member findById(MemberServiceImpl repository, Long id) {
//        return new Member(4L);
//    }
//
//    // 定义测试用例
//    @Test
//    public void testFindById() {
//
//        // 执行测试内容
//        Member member = memberService.findById(1L);
//        Assertions.assertNotNull(member);
//        Assertions.assertEquals(member.getId(), 4L);
//
//        // 验证Mock方法被执行
////        TestableTool.verify("findById").times(1);
//    }
//}*/
