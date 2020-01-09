package com.java.api.list.array;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ArrayListDemo {

    public static void main(String[] args) {


        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("1");
        }
        list.add(1, "2");


        new Thread(new Runnable() {
            @Override
            public void run() {
                list.sort(new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        try {
                           Thread.sleep(500);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return o1.hashCode() - o1.hashCode();
                    }
                });
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                list.sort(new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        try {
                            Thread.sleep(50);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return o1.hashCode() - o1.hashCode();
                    }
                });
            }
        }).start();


    }
}
