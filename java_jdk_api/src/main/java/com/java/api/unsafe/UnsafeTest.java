package com.java.api.unsafe;

import sun.misc.Unsafe;
import sun.misc.VMSupport;

import java.lang.reflect.Field;

public class UnsafeTest {

    public static void main(String[] args) throws Exception {
        ExampleObject exampleObject = new ExampleObject();
        Unsafe unsafe = UnsafeTest.getUnsafe();

        Field value = ExampleObject.class.getDeclaredField("value2");
        long fieldOffset = unsafe.objectFieldOffset(value);
        System.out.println(fieldOffset);
        boolean b = unsafe.compareAndSwapInt(exampleObject, fieldOffset, 0, 2);
        System.out.println(b);
        System.out.println(exampleObject.value);

    }

    private static Unsafe getUnsafe() throws IllegalAccessException {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        return (Unsafe) unsafeField.get(null);
    }

    public static class ExampleObject {

        private int value = 0;
        private int value2 = 0;
    }
}
