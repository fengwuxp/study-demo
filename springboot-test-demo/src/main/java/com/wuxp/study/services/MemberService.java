package com.wuxp.study.services;

import com.wuxp.study.entities.Member;

/**
 * @author wuxp
 */
public interface MemberService {


  Member findById(Long id);

  String findNameById(Long id);
}
