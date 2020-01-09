package com.java8;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamsDemo {

    public static void main(String[] args) {
        List<String> stringCollection = new ArrayList<>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");

//        Filter接受一个predicate接口类型的变量，并将所有流对象中的元素进行过滤。该操
//        作是一个中间操作，因此它允许我们在返回结果的基础上再进行其他的流操作
//（forEach）。ForEach接受一个function接口类型的变量，用来执行对每一个元素
//        的操作。ForEach是一个中止操作。它不返回流，所以我们不能再调用其他的流操
//        作。

//        stringCollection
//                .stream()
//                .filter((s) -> s.startsWith("a"))
//                .forEach(System.out::println);

//        Sorted是一个中间操作，能够返回一个排过序的流对象的视图。流对象中的元素会
//        默认按照自然顺序进行排序，除非你自己指定一个Comparator接口来改变排序规
//        则。
//一定要记住，sorted只是创建一个流对象排序的视图，而不会改变原来集合中元素的顺序。原来string集合中的元素顺序是没有改变的。

//        stringCollection
//                .stream()
//                .sorted()
//                .filter((s) -> s.startsWith("a"))
//                .forEach(System.out::println);
//
////        map是一个对于流对象的中间操作，通过给定的方法，它能够把流对象中的每一个
////        元素对应到另外一个对象上。下面的例子就演示了如何把每个string都转换成大写
////        的string. 不但如此，你还可以把每一种对象映射成为其他类型。对于带泛型结果的
////        流对象，具体的类型还要由传递给map的泛型方法来决定。
//
        stringCollection
                .stream()
                .map(String::toUpperCase)
                .sorted((a, b) -> b.compareTo(a))
                .forEach(System.out::println);
// "DDD2", "DDD1", "CCC", "BBB3", "BBB2", "AAA2", "AAA1"
//
////        stringCollection
////                .stream()
////                .map(Integer::valueOf)
////                .sorted((a, b) -> b.compareTo(a))
////                .forEach(System.out::println);
//
//        //Java7简化了IO操作，把打开IO操作放在try后的括号中即可省略关闭IO的代码。
//        try (Stream<String> lines = Files.lines(Paths.get("d:\\1.txt"), Charset.defaultCharset())) {
//            //可对lines做一些操作
//            lines.forEach((o)->{
//                if(o!=null){
//                    System.out.println(o);
//                }
//
//            });
//        } catch (IOException e) {
//
//        }


        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        List<Integer> list3 = new ArrayList<>();
        Lock lock = new ReentrantLock();

        IntStream.range(0, 10000).forEach(list1::add);
        IntStream.range(0, 10000).parallel().forEach(list2::add);
        IntStream.range(0, 10000).forEach(i -> {
            lock.lock();
            try {
                list3.add(i);
            } finally {
                lock.unlock();
            }
        });

        System.out.println("串行执行的大小：" + list1.size());
        System.out.println("并行执行的大小：" + list2.size());
        System.out.println("加锁并行执行的大小：" + list3.size());
    }

}



