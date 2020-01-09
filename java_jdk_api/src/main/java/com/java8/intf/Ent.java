package com.java8.intf;

public interface Ent {

    default String getDesc() {

        Enum anEnum = (Enum) this;
        return EnumsStore.getDesc(anEnum.getClass(), anEnum.name());
    }
}
