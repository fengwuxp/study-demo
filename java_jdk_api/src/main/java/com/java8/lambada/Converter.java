package com.java8.lambada;

@FunctionalInterface
public interface Converter<F, T> {
    T convert(F from);
}
