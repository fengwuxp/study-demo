package com.java8;

import java.util.function.Function;

public class FormulaTest {

    public static void main(String[] args) {

        Formula formula = new Formula() {
            @Override
            public double calculate(int a) {
                return sqrt(a * 100);
            }
        };
        formula.calculate(100); // 100.0
        formula.sqrt(16);

        Function<String, Integer> toInteger = Integer::valueOf;
        Function<String, String> backToString = toInteger.andThen(String::valueOf);
        Integer integer = toInteger.apply("124");
        String s = backToString.apply("123"); //
        System.out.println(integer);
    }
}
