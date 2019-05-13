package com.netty.example.server.push;


/**
 * 消息推送者
 *
 * @param <T>
 */
public interface MessagePusher<T> {

    boolean push(T message);
}
