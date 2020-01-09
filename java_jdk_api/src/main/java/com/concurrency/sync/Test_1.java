package com.concurrency.sync;

/**
 * synchronized 使用示例2
 *
 * @author wxup
 * @create 2018-07-15 12:43
 **/
public class Test_1 {

    private static int count=0;

    public synchronized static void testSync1(){
        System.out.println(String.format("%s count = %s",Thread.currentThread().getName(),count++));
    }



    public static void main(String[] args) {

    }
}
