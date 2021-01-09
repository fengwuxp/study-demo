package com.wuxp.study.mockito;

import com.wuxp.study.entities.Member;
import com.wuxp.study.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wuxp
 */
@Service
public class MockitoService {

    @Autowired
    private MemberService memberService;

    public Member findById(Long id) {
        return memberService.findById(id);
    }
}
