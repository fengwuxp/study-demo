package com.wuxp.study.mockito.spy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpyHelloDemo {


    public String sayHello() {
        log.info("say hello");
        System.out.println("sayHello，我被真正调用了");
        return "hell word";
    }

    public String sayHello(String text) {
        System.out.println("sayHello，我被真正调用了：text = " + text);
        return text;
    }

    public void noSay() {
        log.info("no say");
        System.out.println("noSay，我被真正调用了");
    }
}
