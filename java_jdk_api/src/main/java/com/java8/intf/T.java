package com.java8.intf;

public enum T implements Ent{

    @Desc("1222")
    X,

    @Desc("2")
    X2;

    public static void main(String[] args) {

        String x = T.X.getDesc();
        System.out.println(x);
    }
}
