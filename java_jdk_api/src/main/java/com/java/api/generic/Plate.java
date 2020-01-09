package com.java.api.generic;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Plate<T> {


    private class Food {
    }

    private class Fruit extends Food {
    }

    private class Apple extends Fruit {
    }

    T item;

    public void set(T a) {
        item = a;
    }

    public T get() {
        return item;
    }

    public void test() {
        Plate<? super Fruit> plate1 = new Plate<>();
        // 正确，plate1 里面装的可能是 Fruit 或 Fruit 的父类，不管是什么，都是 Apple 的父类， 而父类引用指向子类对象是正确的
//        Fruit a = new Apple();
        plate1.set(new Apple());
        // 错误，plate1 里面装的可能是 Fruit 或 Fruit 的父类
        // 而子类引用指向父类对象是错误的，比如 Fruit item = new Food()
        //  plate1.set(new Food());

        Plate<? extends Fruit> plate2 = new Plate<>();
        // 错误，T 不知道是什么类型，可能是 Fruit 的最下面的某个子类，而子类引用指向父类对象是错误的
        // plate2.set(new Fruit());
        // 错误，虽然 Apple 现在是 Fruit 最下面的子类，但以后可能会增加 Apple 的子类，那时 T 就可能是 Apple 的子类，因此也是不合法的
//        Fruit apple = new Apple();
//        plate2.set(apple);

        Plate<? extends Fruit> plate3 = new Plate<>();
        // 正确，plate3 装的都是 Fruit 对象或 Fruit 子类的对象，而不管是什么对象，都可以用 Fruit 引用指向它，父类引用指向子类对象是正确的
        Fruit fruit = plate3.get();
        // 错误，因为里面装的可能是 Fruit 或处于 Fruit 和 Apple 继承链中间的对象，而子类引用指向父类对象是错误的
        // Apple apple = plate3.get();

        Plate<? super Fruit> plate4 = new Plate<>();
        // 错误，plate1 里面装的可能是 Fruit 或 Fruit 的父类，也可能是 Food 的父类，比如 Object
        // Food food = plate4.get();
        // 正确，所有类都是 Object 的子类
        Object object = plate4.get();
    }

}
