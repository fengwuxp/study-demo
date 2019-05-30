package com.rocketmq.example;


import java.util.Collection;

/**
 * 消息投递者
 */
public interface MessageTransmitter<T> {

    boolean transmit(T data,String target);

//    boolean transmit(Collection<T> data);
}
