package com.wuxp.study.controller;

import com.wuxp.study.entities.Member;
import com.wuxp.study.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuxp
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/{id}")
    public Member getMember(@PathVariable(name = "id") Long id) {
        return memberService.findById(id);
    }
}
