package com.java.api.concurrent;

import org.quartz.spi.ThreadExecutor;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 使用复杂对象作为锁对象是不安全的
 * 例如在这个类型的2个对象相等 a.equals(b)=true ，然而 a==b =false
 */
public class Test_01 {

    public static void main(String[] args) throws Exception {

        SynchronizedHandle synchronizedHandle = new SynchronizedHandle();

        ExecutorService executorService = Executors.newFixedThreadPool(30);

        int maxLength = 1000000;

        //
        LockObject[] lockObjects = {new LockObject("1"), new LockObject("1")};

        for (int i = 0; i < maxLength; i++) {
            LockObject lockObject = lockObjects[i % 2];
            executorService.execute(() -> synchronizedHandle.handle(lockObject));
        }
        Thread.sleep(1000);
        executorService.shutdown();
        int count = synchronizedHandle.getCount();
        System.out.println(count);
        System.out.println(maxLength);

    }
}


class LockObject {

    private String id;


    public LockObject(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LockObject)) return false;
        LockObject that = (LockObject) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

class SynchronizedHandle {

    private int count;

    public void handle(final LockObject lockObject) {
        if (lockObject == null) {
            return;
        }

//        synchronized (lockObject) {  //使用这种方式可能会出错
        synchronized (lockObject.getId().intern()) {

            this.count++;
        }

    }

    public int getCount() {
        return count;
    }
}
