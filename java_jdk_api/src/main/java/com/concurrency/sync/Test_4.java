package com.concurrency.sync;


/**
 * synchronized 使用示例5
 *
 * @author wxup
 * @create 2018-07-15 12:43
 **/
public class Test_4 {


    TestName o = new TestName("1");


    public void m1() {
        System.out.println("m1 start");
        synchronized (o) {
            while (true) {
                System.out.println(String.format("%s count = %s", Thread.currentThread().getName(), o.name));
            }
        }
    }

    int a2() {
        int i = 0;
        try {
            i = 1;
            return i;
        } finally {
            i = 10;
            System.out.println(i);
        }
    }

    TestName a1() {
        TestName testName=new TestName("1");
        try {
            testName.name="3";
            return testName;
        } finally {
            testName.name = "4";
            System.out.println(testName.name);
        }
    }


    public static void main(String[] args) throws Exception {
        Test_4 target = new Test_4();
//        System.out.println(target.a2());
        System.out.println(target.a1().name);
        new Thread(new Runnable() {
            @Override
            public void run() {
                target.m1();
            }
        }, "thread - " + 1).start();

        Thread.sleep(1000);
        target.o = new TestName("2");
        new Thread(new Runnable() {
            @Override
            public void run() {
                target.m1();
            }
        }, "thread - " + 2).start();
    }
}

class TestName {

    public String name;

    public TestName(String name) {
        this.name = name;
    }
}
