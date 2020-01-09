package com.java.api.map;

import java.util.HashMap;
import java.util.Map;

public class HasMapDemo {

    public static void main(String[] args) {

        Map<Object, Object> map = new HashMap<>();

        String s1 = "abc";
        String s = new String(new byte[]{10});
        String s2 = String.valueOf(12);

        System.out.println(s == s1.intern());
        System.out.println(s == s2.intern());
        System.out.println(s1 == s2.intern());


//
//        int hashCode = "我".hashCode();
//        Integer x=1;
//        Long y=1L;
//        System.out.println( x.hashCode());
//        System.out.println( y.hashCode());
//        map.put(x,"00");
//        map.put(y,"2-");
//       // System.out.println(map.size());
//        System.out.println(map.get(x));
//        System.out.println(map.get(y));

        // System.out.println((100-1)&100);

    }
}
