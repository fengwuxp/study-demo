package com.wuxp.study.repositories;


import com.wuxp.study.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wuxp
 */

public interface MemberRepository extends JpaRepository<Member, Long> {


  Member findFirstByName(String name);

}
