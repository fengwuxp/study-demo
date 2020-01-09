package com.java8;

import com.java8.lambada.Converter;

/**
 * 与局部变量不同，我们在lambda表达式的内部能获取到对成员变量或静态变量的读
 * 写权。这种访问行为在匿名对象里是非常典型的
 */
public class Lambda4 {

    static int outerStaticNum;
    int outerNum;

    void testScopes() {
        Converter<Integer, String> stringConverter1 = (from) ->
        {
            outerNum = 23;
            return String.valueOf(from);
        };
        Converter<Integer, String> stringConverter2 = (from) ->
        {
            outerStaticNum = 72;
            return String.valueOf(from);
        };
    }

    public static void main(String[] args) {
        Lambda4 lambda4=new Lambda4();
        lambda4.testScopes();
    }

}
