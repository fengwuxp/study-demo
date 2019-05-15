package com.netty.example.server.push;


/**
 * 消息推送者
 *
 * @param <T>
 */
public interface MessagePusher<T> {

    /**
     * 推送
     *
     * @param message
     * @return
     */
    boolean push(T message);
}
