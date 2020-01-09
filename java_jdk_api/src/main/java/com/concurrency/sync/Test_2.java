package com.concurrency.sync;

/**
 * synchronized 使用示例3
 * 同步方法 - 原子性
 *
 * @author wxup
 * @create 2018-07-15 12:43
 **/
public class Test_2 implements Runnable {

    private int count = 0;


    public synchronized void run() {
        System.out.println(String.format("%s count = %s", Thread.currentThread().getName(), count++));
    }

    public static void main(String[] args) {
        Test_2 test_2 = new Test_2();
        for (int i = 0; i < 5; i++) {
            new Thread(test_2, "thread - " + i).start();
        }
    }
}
