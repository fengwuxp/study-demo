package com.wuxp.study.mockito.mock;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MockHelloDemo {


    public String sayHello() {
        return "hell word";
    }

    public String sayHello(String text) {
        log.info("say hello：{}",text);
        return text;
    }

    public void noSay() {

    }
}
