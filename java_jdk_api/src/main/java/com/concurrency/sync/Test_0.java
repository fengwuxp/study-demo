package com.concurrency.sync;

/**
 * synchronized 使用示例1
 * 锁对象：synchronized(this)和synchronized方法都是锁当前对象
 *
 * @author wxup
 * @create 2018-07-15 12:43
 **/
public class Test_0 {

    private int count=0;

    private Object o =new Object();

    public void testSync1(){
        synchronized (o){
            System.out.println(String.format("%s count = %s",Thread.currentThread().getName(),count++));
        }
    }

    public void testSync2(){
        synchronized (this){
            System.out.println(String.format("%s count = %s",Thread.currentThread().getName(),count++));
        }
    }

    public synchronized void testSync3(){
        System.out.println(String.format("%s count = %s",Thread.currentThread().getName(),count++));
    }

    public static void main(String[] args) {
        Test_0 test_0=new Test_0();

        Thread thread_0=new Thread(new Runnable(){
            @Override
            public void run() {
                test_0.testSync1();
                test_0.testSync2();
                test_0.testSync3();
            }
        });
        Thread thread_1=new Thread(new Runnable(){
            @Override
            public void run() {
                test_0.testSync1();
                test_0.testSync2();
                test_0.testSync3();
            }
        });

        Thread thread_2=new Thread(new Runnable(){
            @Override
            public void run() {
                test_0.testSync1();
                test_0.testSync2();
                test_0.testSync3();
            }
        });

        thread_0.start();
        thread_1.start();
        thread_2.start();
    }
}
