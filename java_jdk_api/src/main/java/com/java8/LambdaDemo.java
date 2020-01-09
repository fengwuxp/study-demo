package com.java8;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.java8.lambada.*;

public class LambdaDemo {

    public static void main(String[] args) {

//        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
//        Collections.sort(names, new Comparator<String>() {
//            @Override
//            public int compare(String a, String b) {
//                return b.compareTo(a);
//            }
//        });
//
//        Collections.sort(names, (String a, String b) -> {
//            return b.compareTo(a);
//        });
//
//        Collections.sort(names, (String a, String b) -> b.compareTo(a));
//
//
//        Collections.sort(names, (a, b) -> b.compareTo(a));
//
//        System.out.println(names);
//        Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
//        Integer converted = converter.convert("123");
//        System.out.println(converted); // 123

        //Java 8 允许你通过::关键字获取方法或者构造函数的的引用，而且，我们还可以对一个对象的方法进行引用：

//        Converter<String, Integer> converter = Integer::valueOf;
//        Integer converted = converter.convert("123");
//        System.out.println(converted);

//        Something something = new Something();
//        Converter<String, String> converter = something::startsWith;
//        String converted = converter.convert("Java");
//        System.out.println(converted);
        PersonFactory<Person> personFactory = Person::new;
        Person person = personFactory.create("Peter", "Parker");

    }
}

class Something {
    String startsWith(String s) {
        return String.valueOf(s.charAt(0));
    }
}

class Person {
    String firstName;

    String lastName;

    Person() {
    }

    Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Person{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

@FunctionalInterface
interface PersonFactory<P extends Person> {
    P create(String firstName, String lastName);
}
