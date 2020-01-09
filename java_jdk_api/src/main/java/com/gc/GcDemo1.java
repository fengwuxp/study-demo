package com.gc;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/alibaba/arthas
 *
 * https://www.jianshu.com/p/8807792d8d3f
 * 
 */
public class GcDemo1 {

    public static void main(String[] args) throws Exception {

        // -XX:+PrintGC -Xmx16m -Xms16m -Xmn16m

        List<String> list = new ArrayList<String>(16);

        int maxSize = 10;

        while (true) {
            for (int i = 0; i < maxSize; i++) {
//                if (list.size()<=i) {
//                    list.add(i,RandomStringUtils.randomAlphabetic(i));
//                }else {
//                    list.set(i,RandomStringUtils.randomAlphabetic(i));
//                }
                list.add(i, RandomStringUtils.randomAlphabetic(i));
            }
            if (System.currentTimeMillis() % 2 == 0) {
                System.out.println("--------------");
                System.gc();
                System.out.println("--------------");
            }

//            list.clear();
//            list = new ArrayList<>();
        }


    }
}
