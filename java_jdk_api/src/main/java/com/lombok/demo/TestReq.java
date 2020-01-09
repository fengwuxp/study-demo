package com.lombok.demo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestReq {

    private Long id;

    private String name;

    private int age;

    private Boolean sex;


    public static void main(String[] args) {
        TestReq testReq = TestReq.builder()
                .age(0)
                .id(1L)
                .name("张三")
                .sex(false)
                .build();
        System.out.println(testReq.getAge());
    }
}
