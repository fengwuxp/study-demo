package com.java.api.jol;

import org.openjdk.jol.info.ClassLayout;
import sun.misc.VMSupport;

public class JolTest {

    public static void main(String[] args) throws Exception {

        // 将jvm偏向锁启动时延设置为0 默认为4秒
        // -XX:BiasedLockingStartupDelay=0

        Thread.sleep(4 * 1000 + 50);

        Object o = new Object();
        String s = ClassLayout.parseInstance(o).toPrintable();
        System.out.println(s);

        System.out.println("=========================");
//        int i = o.hashCode();
        synchronized (o) {
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
        System.out.println("=========================");
//        int i = o.hashCode();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

    }
}
