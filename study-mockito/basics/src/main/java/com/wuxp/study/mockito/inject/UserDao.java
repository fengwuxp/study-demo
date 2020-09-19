package com.wuxp.study.mockito.inject;

import java.util.Random;

public class UserDao {

    public Long getUserId(String userName) {
        System.out.println("查找的用户名称："+userName);
        return new Random().nextLong();
    }
}
