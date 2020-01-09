package com.java8;

public interface Formula {

    double calculate(int a);

    /**
     * Java 8 允许我们使用default关键字，为接口声明添加非抽象的方法实现。
     * 这个特性又被称为扩展方法
     * @param a
     * @return
     */
    default double sqrt(int a) {
        return Math.sqrt(a);
    }
}
