package com.concurrency.sync;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch 使用示例
 * @author wxup
 * @create 2018-07-15 12:43
 **/
public class Test_5 {

    TestContainer<Integer> container = new TestContainer<>();

    CountDownLatch latch = new CountDownLatch(1);

    CountDownLatch latch_2 = new CountDownLatch(1);


    void m0() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (container.size() != 5) {
                    try {
                        latch.await();
                    } catch (Exception e) {

                    }
                }
                System.out.println("size=" + container.size());
                latch_2.countDown();
            }
        }).start();
    }

    void m1() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 10; i++) {
                    container.add(i);
                    System.out.println("add item ,size=" + container.size());
                    if (container.size() == 5) {
                        latch.countDown();
                        if (latch.getCount() == 0) {
                            try {
                                latch_2.await();
                            } catch (Exception e) {

                            }
                        }
                    }

                }

            }
        }).start();
    }

    public static void main(String[] args) {
        Test_5 test_5 = new Test_5();
        test_5.m0();
        test_5.m1();
    }

}

class TestContainer<T> {
    List<T> list = new ArrayList<>();


    public void add(T o) {
        list.add(o);
    }

    public int size() {

        return list.size();
    }

}
