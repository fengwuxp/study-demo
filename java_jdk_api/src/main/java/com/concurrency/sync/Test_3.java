package com.concurrency.sync;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * synchronized 使用示例4
 * 死锁例子
 * 死锁的条件：多个线程（至少2个） 相互申请对方持有的锁
 *
 * @author wxup
 * @create 2018-07-15 12:43
 **/
public class Test_3 {

    private int count = 0;

    private Object o1 = new Object();

    private Object o2 = new Object();



    public void m1() {
        System.out.println("m1 start");
        synchronized (o1) {
            System.out.println(String.format("%s count = %s", Thread.currentThread().getName(), count++));
//            try {
//                Thread.sleep(100);
//            } catch (Exception e) {
//
//            }
            this.m4();
        }
        System.out.println("m1 end");
    }

    public void m2() {
        System.out.println("m2 start");
        synchronized (o2) {
            System.out.println(String.format("%s count = %s", Thread.currentThread().getName(), count++));
//            try {
//                Thread.sleep(50);
//            } catch (Exception e) {
//
//            }
            this.m3();
        }

        System.out.println("m2 end");
    }

    public void m3() {
        synchronized (o1) {
            System.out.println(String.format("%s count = %s", Thread.currentThread().getName(), count++));
        }
    }

    public void m4() {
        synchronized (o2) {
            System.out.println(String.format("%s count = %s", Thread.currentThread().getName(), count++));
        }
    }


    public static void main(String[] args) {
        Test_3 target = new Test_3();
        new Thread(new Runnable() {
            @Override
            public void run() {
                target.m1();
            }
        }, "thread - " + 1).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                target.m2();
            }
        }, "thread - " + 2).start();
    }
}
