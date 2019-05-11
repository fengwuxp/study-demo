package com.wuxp.study.querydsl.entities;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_member")
@Data
@Accessors(chain = true)
public class Member implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String name;

    private String nickname;

    private String mobilePhone;

    @Temporal(TemporalType.TIMESTAMP)
    private Date birthday;
}
